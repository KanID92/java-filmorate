package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping
public class UserController {

    private final UserService userService;
    private final ValidationService validationService;
    private long idCounter = 0;

    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @GetMapping("/users")
    public Collection<User> getAll() {
        log.info("==> GET /users ");
        log.info("<== GET /users Список пользователей размером: "
                + userService.getAll().values().size() + " возвращен");
        return userService.getAll().values();
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable long id) {
        log.info("==> GET /users/" + id);
        User user = userService.getById(id);
        log.info("<== GET /users/" + id + "  Пользователь: " + user);
        return user;
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        log.info("==> GET /users/" + id + "/friends");
        Collection<User> userFriends = userService.getFriends(id);
        log.info("<== GET /users/" + id + "/friends" + " Количество друзей: " + userFriends.size());
        return userFriends;
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info("==> GET /users/" + id + "/friends/common/" + otherId);
        Collection<User> usersCommonFriends = userService.getCommonFriends(id, otherId);
        log.info("<== GET /users/" + id + "/friends/common/" + otherId +
                "Количество общих друзей: " + usersCommonFriends.size());
        return usersCommonFriends;
    }

    @PostMapping("/users")
    public User save(@RequestBody User user) {
        log.info("==> GET /users " + user);
        validationService.validateCreate(user);
        user.setId(getNextId());
        log.info("<== GET /users " + user);
        return userService.save(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        log.info("==> PUT /users " + user);
        validationService.validateUpdate(user, userService.getAll());
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("<== PUT /users " + user);
        return userService.save(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriend(id, friendId);
    }

    private long getNextId() {
        return ++idCounter;
    }

}
