package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationService;
import ru.yandex.practicum.filmorate.validation.ValidationServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    ValidationService validationService = new ValidationServiceImpl();
    FilmController filmController = new FilmController(validationService);
    Film film1;
    Film film2;
    Film film3;


    @BeforeEach
    void setBeforeAll() {
        film1 = new Film();
        film1.setId(1L);
        film1.setName("Однажды в Голливуде");
        film1.setDescription("Актеры: Леонардо ДиКаприо, Брэд Питт");
        film1.setReleaseDate(LocalDate.of(2019, 5, 21));
        film1.setDuration(185);


        film2 = new Film();
        film2.setId(2L);
        film2.setName("Пекло");
        film2.setDescription("Актеры: Киллиан Мёрфи, Роуз Бирн");
        film2.setReleaseDate(LocalDate.of(2007, 3, 1));
        film2.setDuration(185);

        film3 = new Film();
        film3.setId(3L);
        film3.setName("Сквозь горизонт");
        film3.setDescription("Актеры: Лоренс Фишберн, Сэм Нилл");
        film3.setReleaseDate(LocalDate.of(1997, 3, 1));
        film3.setDuration(200);
    }

    @Test
    void shouldValidateFilm() {
        assertDoesNotThrow(() -> validationService.validateCreate(film1));
    }

    @Test
    void shouldNotValidateBadReleaseDateFilm() {
        film2.setReleaseDate(LocalDate.of(1007, 3, 1));
        assertThrows(ValidationException.class, () -> validationService.validateCreate(film2));
    }

    @Test
    void shouldNotValidateNegativeDurationFilm() {
        film2.setDuration(-180);
        assertThrows(ValidationException.class, () -> validationService.validateCreate(film2));
    }

    @Test
    void shouldNotValidateTooLongDescriptionFilm() {
        String description = "x".repeat(201);

        film2.setDescription(description);
        assertThrows(ValidationException.class, () -> validationService.validateCreate(film2));
    }

    @Test
    void shouldNotValidateNoNameFilm() {
        film2.setName(null);
        assertThrows(ValidationException.class, () -> validationService.validateCreate(film2));
        film2.setName("");
        assertThrows(ValidationException.class, () -> validationService.validateCreate(film2));
    }


}
