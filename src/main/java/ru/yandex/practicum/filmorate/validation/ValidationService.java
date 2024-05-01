package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface ValidationService {
    void validateCreate(Film film);

    void validateUpdate(Film film, Map<Long, Film> filmMap);

    void validateCreate(User user);

    void validateUpdate(User user, Map<Long, User> userMap);


}
