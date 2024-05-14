package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;
import java.util.Set;

public interface UserStorage {

    User getById(long userId);

    User save(User user);

    void delete(long userId);

    User update(User user);

    Set<Long> getUserFriends(long userId);

    Map<Long, User> getAll();

}
