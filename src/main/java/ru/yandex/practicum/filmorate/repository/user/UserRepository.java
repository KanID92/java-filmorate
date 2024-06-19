package ru.yandex.practicum.filmorate.repository.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getById(long userId);

    User save(User user);

    void deleteById(Long userId);

    User update(User user);

    Collection<User> getFriendsByID(Long userId);

    Collection<User> getAll();

    Collection<User> getCommonFriends(long userId1, long userId2);

}
