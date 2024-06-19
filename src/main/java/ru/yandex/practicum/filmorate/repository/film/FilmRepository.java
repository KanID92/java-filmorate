package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

public interface FilmRepository {

    Film save(Film film);

    void deleteById(long filmId);

    Film update(Film film);

    Collection<Film> getAll();

    Optional<Film> getById(long filmId);

    Collection<Film> getTopPopular(long count);

    LinkedHashSet<Genre> getGenres(long filmId);

}
