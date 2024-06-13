package ru.yandex.practicum.filmorate.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@RequiredArgsConstructor
public class FilmExtractor implements ResultSetExtractor<Film> {

    @Override
    public Film extractData(ResultSet rs) throws SQLException, DataAccessException {
        Film film = null;
        while (rs.next()) {
            if (film == null) {
                film = new Film();
                film.setId(rs.getInt("film_id"));
                film.setName(rs.getString("name"));
                film.setDescription(rs.getString("description"));
                film.setReleaseDate(LocalDate.parse(rs.getString("release_date")));
                film.setDuration(rs.getLong("duration_in_min"));
                film.setMpa(new MPARating(
                        rs.getInt("mpa_rating_id"), rs.getString("MPA_RATING.name")));
            }
            int genreId = rs.getInt("genre_id");
            String genreName = rs.getString("genre_name");
            if (genreId != 0 || genreName != null) {
                film.getGenres().add(
                        new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
            }
        }
        return film;
    }
}
