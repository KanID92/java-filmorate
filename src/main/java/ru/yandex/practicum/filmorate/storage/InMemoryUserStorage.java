package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private final ValidationService validationService;

    @Override
    public User getById(long userId) {
        return validationService.validateUserExisting(userId, users);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        validationService.validateUserExisting(user.getId(), users);
        return save(user);
    }

    @Override
    public void delete(long userId) {
        validationService.validateUserExisting(userId, users);
        users.remove(userId);
    }

    @Override
    public Map<Long, User> getAll() {
        return users;
    }


}
