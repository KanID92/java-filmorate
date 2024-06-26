package ru.yandex.practicum.filmorate.service.validation;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.User;

@Service
public interface ValidationService {
    void validateNewData(Film film);

    void validateNewData(User user);

    void validateNewData(Review review);

    void validateNewData(Director director);
}
