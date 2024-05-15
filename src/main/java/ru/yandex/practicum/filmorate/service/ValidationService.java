package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Service
public interface ValidationService {
    void validateNewData(Film film);

    void validateNewData(User user);

    Film validateFilmExisting(long filmId, Map<Long, Film> filmMap);

    User validateUserExisting(long userId, Map<Long, User> userMap);


}
