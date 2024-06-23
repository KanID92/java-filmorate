package ru.yandex.practicum.filmorate.repository.film;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.film.mapper.AllFilmsExtractor;
import ru.yandex.practicum.filmorate.repository.film.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.repository.genre.mapper.GenreRowMapper;

import java.sql.Date;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {

    private final NamedParameterJdbcOperations jdbs;


    @Override
    public Optional<Film> getById(long filmId) {
        String sql = "SELECT * FROM films AS f " +
                "LEFT OUTER JOIN MPA_rating AS mr ON f.mpa_rating_id = mr.mpa_rating_id " +
                "WHERE f.film_id = :filmId";
        try {
            Film film = jdbs.queryForObject(sql, Map.of("filmId", filmId), new FilmRowMapper());
            if (film.getGenres() != null) {
                LinkedHashSet<Genre> genres = getGenres(filmId);
                film.setGenres(genres);
            }
            return Optional.ofNullable(film);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Film with id " + filmId + " not found");
        }

    }

    @Override
    public Film save(Film film) {
        GeneratedKeyHolder keyHolderFilms = new GeneratedKeyHolder();


        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", Date.valueOf(film.getReleaseDate()))
                .addValue("duration_in_sec", film.getDuration())
                .addValue("mpa_rating_id", film.getMpa().getId());


        jdbs.update("INSERT INTO FILMS (" +
                        "NAME, DESCRIPTION, RELEASE_DATE, DURATION_IN_MIN, MPA_RATING_ID) " +
                        "VALUES (:name, :description, :release_date, :duration_in_sec, :mpa_rating_id)",
                params, keyHolderFilms, new String[]{"film_id"});


        film.setId(keyHolderFilms.getKeyAs(Long.class));

        createFilmGenresBond(film);

        return getById(film.getId()).orElseThrow();


    }

    @Override
    public Film update(Film film) {

        if (getById(film.getId()).isPresent()) {

            String sqlUpdateFilm = "UPDATE FILMS SET " +
                    "NAME = :name, " +
                    "DESCRIPTION = :description, " +
                    "RELEASE_DATE = :releaseDate, " +
                    "DURATION_IN_MIN = :duration, " +
                    "MPA_RATING_ID = :mpa_rating_id " +
                    "WHERE FILM_ID = :filmId";

            jdbs.update(sqlUpdateFilm, Map.of(
                    "filmId", film.getId(),
                    "name", film.getName(),
                    "description", film.getDescription(),
                    "releaseDate", film.getReleaseDate(),
                    "duration", film.getDuration(),
                    "mpa_rating_id", film.getMpa().getId()));

            String sqlDeleteGenresBond = "DELETE FROM FILM_GENRE WHERE FILM_ID = :filmId";
            jdbs.update(sqlDeleteGenresBond, Map.of("filmId", film.getId()));

            //createFilmMpaBond(film);
            createFilmGenresBond(film);

            return film;

        } else {
            throw new NotFoundException("Фильм для обновления с id " + film.getId() + " не найден");
        }

    }

    @Override
    public void deleteById(long filmId) {
        String query = "DELETE FROM FILMS WHERE FILM_ID = :filmId";
        jdbs.update(query, Map.of("filmId", filmId));
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "Select * FROM films AS f " +
                "LEFT JOIN film_genre AS fg ON fg.film_id = f.film_id " +
                "LEFT JOIN genres AS g ON g.genre_id = fg.genre_id " +
                "LEFT JOIN MPA_rating AS mr ON f.mpa_rating_id = mr.mpa_rating_id";
        return jdbs.query(sql, new AllFilmsExtractor());
    }


    @Override
    public Collection<Film> getTopPopular(long countTop) {

        String sqlTopFilms = "SELECT f.*, mr.name " +
                "FROM FILMS AS f " +
                "LEFT JOIN LIKES AS l ON f.film_id = l.film_id " +
                "LEFT JOIN MPA_RATING AS mr ON f.mpa_rating_id = mr.mpa_rating_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(l.film_id) DESC " +
                "LIMIT :countTop";

        Collection<Film> topFilms = jdbs.query(sqlTopFilms, Map.of("countTop", countTop),
                new AllFilmsExtractor());
        System.out.println(topFilms);
        List<Long> topFilmsIds = Objects.requireNonNull(topFilms).stream().map(Film::getId).toList();
        Map<Long, LinkedHashSet<Genre>> genres = getGenresForFilms(topFilmsIds);

        topFilms.forEach(film -> film.setGenres(genres.getOrDefault(film.getId(), new LinkedHashSet<>())));

        return topFilms;

    }


    @Override
    public LinkedHashSet<Genre> getGenres(long filmId) {
        String sql = "Select fg.genre_id, g.genre_name " +
                "FROM film_genre AS fg " +
                "LEFT OUTER JOIN genres AS g ON fg.genre_id = g.genre_id " +
                "WHERE fg.film_id = :filmId " +
                "ORDER BY g.genre_id";
        return new LinkedHashSet<>(jdbs.query(sql, Map.of("filmId", filmId), new GenreRowMapper()));
    }

    private void createFilmGenresBond(Film film) {
        if (film.getGenres() != null) {

            String sql = "Select genre_id from genres";
            List<Integer> genresFromDb = jdbs.queryForList(sql, new HashMap<>(), Integer.class);
            for (Genre genre : film.getGenres()) {
                if (!genresFromDb.contains(genre.getId())) {
                    throw new EmptyResultDataAccessException(genre.getId());
                }
            }

            GeneratedKeyHolder keyHolderGenres = new GeneratedKeyHolder();

            final List<Genre> genres = new ArrayList<>(film.getGenres());
            SqlParameterSource[] paramsGenres = new MapSqlParameterSource[genres.size()];
            for (int i = 0; i < genres.size(); i++) {
                paramsGenres[i] = new MapSqlParameterSource()
                        .addValue("filmId", film.getId())
                        .addValue("genreId", genres.get(i).getId());
            }

            jdbs.batchUpdate("INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) " +
                            "VALUES (:filmId, :genreId)",
                    paramsGenres, keyHolderGenres, new String[]{"film_genre_id"});
        }
    }

    private Map<Long, LinkedHashSet<Genre>> getGenresForFilms(List<Long> filmIds) {
        String sql = "SELECT fg.FILM_ID, g.Genre_ID, g.GENRE_NAME " +
                "FROM FILM_GENRE AS fg " +
                "JOIN GENRES AS g ON fg.GENRE_ID = g.GENRE_ID " +
                "WHERE fg.FILM_ID IN (:filmIds)";
        if (filmIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Map<String, Object>> rows = jdbs.queryForList(sql, Map.of("filmIds", filmIds));
        Map<Long, LinkedHashSet<Genre>> genresMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long filmId = (Long) row.get("FILM_ID");
            Genre genre = new Genre((Integer) row.get("GENRE_ID"), (String) row.get("GENRE_NAME"));

            genresMap.computeIfAbsent(filmId, k -> new LinkedHashSet<>()).add(genre);
        }

        return genresMap;
    }

}

