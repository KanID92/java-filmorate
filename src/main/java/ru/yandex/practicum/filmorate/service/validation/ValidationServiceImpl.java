package ru.yandex.practicum.filmorate.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
@Service
public class ValidationServiceImpl implements ValidationService {

    public static final LocalDate FIRST_FILM_DATE = LocalDate.of(1895, Month.DECEMBER, 28);

    //USER VALIDATIONS

    @Override
    public void validateNewData(User user) {
        if (!user.getEmail().contains("@") || user.getEmail().isBlank()) {
            log.error("Неверный формат электронной почты");
            throw new ValidationException("Неверный формат электронной почты");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("Неверный формат логина");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    //FILM VALIDATIONS

    @Override
    public void validateNewData(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Название фильма - пустое.");
            throw new ValidationException("Название фильма не должно быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Описание фильма - более 200 символов.");
            throw new ValidationException("Максимальная длина описания фильма - не более 200 символов.");
        }

        if (film.getReleaseDate().isBefore(FIRST_FILM_DATE)) {
            log.warn("Дата релиза фильма - раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза фильма - не раньше 28 декабря 1895 года.");
        }

        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма - отрицательное или нулевой значение");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом.");
        }
    }

}