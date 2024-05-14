package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Service
public interface ValidationService {
    void validateCreate(Film film);

    void validateUpdate(Film film, Map<Long, Film> filmMap);

    void validateCreate(User user);

    void validateUpdate(User user, Map<Long, User> userMap);

    Film validateFilmExisting(long filmId, Map<Long, Film> filmMap);

    User validateUserExisting(long userId, Map<Long, User> userMap);


}
