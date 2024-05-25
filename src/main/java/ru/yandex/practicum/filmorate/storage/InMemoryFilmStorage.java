package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 0;

    @Override
    public Film getById(long filmId) {
        Film film = films.get(filmId);
        if (film == null) {
            log.error("Фильма с данным ID = " + filmId + " не найдено");
            throw new NotFoundException("Фильм не найден");
        }
        return film;
    }

    @Override
    public Film save(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film update(Film film) {
        getById(film.getId());
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public void delete(long filmId) {
        getById(filmId);
        films.remove(filmId);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    private long getNextId() {
        return ++idCounter;
    }

}
