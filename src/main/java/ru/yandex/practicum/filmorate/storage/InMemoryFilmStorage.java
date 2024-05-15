package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final ValidationService validationService;

    @Override
    public Film getById(long filmId) {
        return validationService.validateFilmExisting(filmId, films);
    }

    @Override
    public Film save(Film film) {
        films.put(film.getId(), film);
        return films.get(film.getId());
    }

    @Override
    public Film update(Film film) {
        validationService.validateFilmExisting(film.getId(), films);
        return save(film);
    }

    @Override
    public void delete(long filmId) {
        validationService.validateFilmExisting(filmId, films);
        films.remove(filmId);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }


}
