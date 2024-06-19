package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPARating;

import java.util.Collection;
import java.util.List;

public interface FilmService {

    Film getById(long filmId);

    Film save(Film film);

    Film update(Film film);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    Collection<Film> getAll();

    Collection<Genre> getAllGenres();

    List<Genre> getAllFilmGenres(long filmId);

    MPARating getMPARatingById(int filmId);

    Collection<MPARating> getAllMPARatings();

    Collection<Film> getMostLikedFilms(long limit);

    Genre getGenreById(Integer genreId);

}
