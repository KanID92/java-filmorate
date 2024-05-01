package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationService;
import ru.yandex.practicum.filmorate.validation.ValidationServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    Map<Long, User> users = new HashMap<>();
    ValidationService validationService = this.validationService = new ValidationServiceImpl();

    @GetMapping
    public Collection<User> getAllUsers() {
        log.info("==> GET /users ");
        log.info("<== GET /users Список пользователей размером: " + users.values().size() + " возвращен");
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        log.info("==> GET /users " + user);
        validationService.validateCreate(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("<== GET /users " + user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("==> PUT /users " + user);
        validationService.validateUpdate(user, users);
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("<== PUT /users " + user);
        return user;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
