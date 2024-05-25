package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 0;

    @Override
    public User getById(long userId) {
        User user = users.get(userId);
        if (user == null) {
            log.error("Пользователя с переданным ID = " + userId + " не найдено");
            throw new NotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public User save(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        getById(user.getId());
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public void delete(long userId) {
        getById(userId);
        users.remove(userId);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    private long getNextId() {
        return ++idCounter;
    }

}
