package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User getById(long userId) {
        return users.get(userId);
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        return save(user);
    }

    @Override
    public void delete(long userId) {
        users.remove(userId);
    }

    @Override
    public Set<Long> getUserFriends(long userId) {
        return users.get(userId).getFriends();
    }

    @Override
    public Map<Long, User> getAll() {
        return users;
    }


}
