package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {

    Film save(Film film);

    void delete(long filmId);

    Film update(Film film);

    Map<Long, Film> getAll();

    Film getById(long filmId);

}