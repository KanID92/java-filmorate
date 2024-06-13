package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.BaseUserService;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserControllerTest {

    private final ValidationService validationService;
    private final BaseUserService baseUserService;

    private final UserController userController;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setBeforeAll() {
        user1 = new User();
        user1.setId(1L);
        user1.setName("Пользователь №1");
        user1.setEmail("1@mail.ru");
        user1.setLogin("User№1");
        user1.setBirthday(LocalDate.of(2000, 2, 1));


        user2 = new User();
        user2.setId(2L);
        user2.setName("Пользователь №2");
        user2.setEmail("2@ya.ru");
        user2.setLogin("User№2");
        user2.setBirthday(LocalDate.of(1992, 5, 3));

        user3 = new User();
        user3.setId(3L);
        user3.setEmail("3@ya.ru");
        user3.setLogin("User№3");
        user3.setBirthday(LocalDate.of(1932, 1, 15));
    }

    @Test
    void shouldValidateUser() {
        assertDoesNotThrow(() -> validationService.validateNewData(user1));
    }

    @Test
    void shouldNotValidateBadEmail() {
        user1.setEmail("1ya.ru");
        assertThrows(ValidationException.class, () -> validationService.validateNewData(user1));
        user1.setEmail(" ");
        assertThrows(ValidationException.class, () -> validationService.validateNewData(user1));
    }

    @Test
    void shouldNotValidateEmptyLogin() {
        user1.setLogin(" ");
        assertThrows(ValidationException.class, () -> validationService.validateNewData(user1));
    }

    @Test
    void shouldNotValidateBirthdayDate() {
        user1.setBirthday(LocalDate.of(2932, 1, 15));
        assertThrows(ValidationException.class, () -> validationService.validateNewData(user1));
    }

    @Test
    void shouldSetNameAsLogin() {
        assertDoesNotThrow(() -> validationService.validateNewData(user3));
        assertEquals(user3.getName(), user3.getLogin());
    }

}


