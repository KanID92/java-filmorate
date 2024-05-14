package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final ValidationService validationService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService, ValidationService validationService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.validationService = validationService;
    }

    public Film getById(long filmId) {
        return validationService.validateFilmExisting(filmId, filmStorage.getAll());
    }

    public Film save(Film film) {
        return filmStorage.save(film);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getAll().get(filmId);
        User user = userService.getAll().get(userId);
        if (film == null) {
            throw new NotFoundException("Фильм для добавления лайка не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь, добавляющий лайк фильму - не найден");
        }
        film.getUsersLikes().add(userId);
    }

    public void deleteLike(long filmId, long userId) {
        Film film = filmStorage.getAll().get(filmId);
        User user = userService.getAll().get(userId);
        if (film == null) {
            throw new NotFoundException("Фильм для удаления лайка не найден");
        }
        if (user == null) {
            throw new NotFoundException("Пользователь, удаляющий лайк фильму - не найден");
        }

        filmStorage.getAll().get(filmId).getUsersLikes().remove(userId);
    }

    public Map<Long, Film> getAll() {
        return filmStorage.getAll();
    }

    public Collection<Film> getMostLikedFilms(long limit) {
        return List.copyOf(filmStorage.getAll().values())
                .stream()
                .sorted(new Comparator<Film>() {
                    @Override
                    public int compare(Film f1, Film f2) {
                        return f2.getUsersLikes().size() - f1.getUsersLikes().size();
                    }
                })
                .limit(limit)
                .toList();
    }
}
