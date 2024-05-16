package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final ValidationService validationService;

    public Film getById(long filmId) {
        return filmStorage.getById(filmId);
    }

    public Film save(Film film) {
        validationService.validateNewData(film);
        return filmStorage.save(film);
    }

    public Film update(Film film) {
        validationService.validateNewData(film);
        return filmStorage.update(film);
    }

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getById(filmId);
        userService.getById(userId);
        film.getUsersLikes().add(userId);
    }

    public void deleteLike(long filmId, long userId) {
        Film film = filmStorage.getById(filmId);
        userService.getById(userId);
        film.getUsersLikes().remove(userId);
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public Collection<Film> getMostLikedFilms(long limit) {
        return List.copyOf(filmStorage.getAll())
                .stream()
                .sorted((f1, f2) -> f2.getUsersLikes().size() - f1.getUsersLikes().size())
                .limit(limit)
                .toList();
    }
}
