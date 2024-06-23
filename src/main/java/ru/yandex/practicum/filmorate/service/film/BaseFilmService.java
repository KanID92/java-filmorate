package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.like.LikeRepository;
import ru.yandex.practicum.filmorate.repository.mpa.MPARatingRepository;
import ru.yandex.practicum.filmorate.service.director.DirectorService;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.validation.ValidationService;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ValidationService validationService;
    private final MPARatingRepository mpaRatingRepository;
    private final DirectorService directorService;

    @Override
    public Film getById(long filmId) {
        Film film = filmRepository.getById(filmId).orElseThrow(
                () -> new NotFoundException("Фильм с id=" + filmId + " не найден"));
        film.setGenres(filmRepository.getGenres(filmId));
        film.setDirectors(directorService.getDirectors(filmId));
        return film;
    }

    @Override
    public Film save(Film film) {
        validationService.validateNewData(film);
        try {
            mpaRatingRepository.getMPARatingById(film.getMpa().getId());
        } catch (NotFoundException e) {
            throw new ValidationException("Рейтинг MPA с данным id - не найден");
        }
        Film filmSaved = filmRepository.save(film);
        filmSaved.setGenres(filmRepository.getGenres(film.getId()));
        filmSaved.setDirectors(directorService.getDirectors(film.getId()));
        return filmSaved;
    }

    @Override
    public Film update(Film film) {
        validationService.validateNewData(film);
        Film filmUpdated = filmRepository.update(film);
        filmUpdated.setGenres(filmRepository.getGenres(film.getId()));
        return filmUpdated;
    }

    @Override
    public void addLike(long filmId, long userId) {
        filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id=" + filmId + " не найден"));
        userService.getById(userId);
        likeRepository.add(userId, filmId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        filmRepository.getById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм с id=" + filmId + " не найден"));
        userService.getById(userId);
        likeRepository.delete(userId, filmId);
    }

    @Override
    public Collection<Film> getAll() {
        return filmRepository.getAll();
    }

    @Override
    public Collection<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @Override
    public List<Genre> getAllFilmGenres(long filmId) {
        return genreRepository.getAllFilmGenres(filmId);
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        return genreRepository.getGenreById(genreId);
    }

    @Override
    public void deleteFilm(long filmId) {
        filmRepository.deleteById(filmId);
    }

    @Override
    public Collection<Film> getDirectorFilmsSorted(long directorId, String sortBy) {
        return filmRepository.getSortedFilmsByDirector(directorId, sortBy);
    }

    @Override
    public Collection<MPARating> getAllMPARatings() {
        return mpaRatingRepository.getAllMPARatings();
    }

    @Override
    public MPARating getMPARatingById(int mpaId) {
        return mpaRatingRepository.getMPARatingById(mpaId);
    }

    @Override
    public Collection<Film> getMostLikedFilms(Long limit, Integer genreId, Integer year) {
        return filmRepository.getTopPopular(limit, genreId, year);
    }
}
