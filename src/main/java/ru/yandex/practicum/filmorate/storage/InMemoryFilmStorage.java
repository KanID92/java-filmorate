package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film getById(long filmId) {
        return films.get(filmId);
    }

    @Override
    public Film save(Film film) {
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film update(Film film) {
        return save(film);
    }

    @Override
    public void delete(long filmId) {
        films.remove(filmId);
    }

    @Override
    public Map<Long, Film> getAll() {
        return films;
    }

}
