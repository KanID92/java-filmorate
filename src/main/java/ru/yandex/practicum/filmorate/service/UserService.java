package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    private final ValidationService validationService;

    public User getById(long userId) {
        return userStorage.getById(userId);
    }

    public void addFriend(long userId1, long userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        user1.getFriends().add(userId2);
        user2.getFriends().add(userId1);
    }

    public void deleteFriend(long userId1, long userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        user1.getFriends().remove(userId2);
        user2.getFriends().remove(userId1);
    }

    public Collection<User> getFriends(long userId) {
        User user = userStorage.getById(userId);
        Set<Long> userFriendsIds = user.getFriends();
        Set<User> userFriends = new HashSet<>();

        for (Long userFriendId : userFriendsIds) {
            userFriends.add(userStorage.getById(userFriendId));
        }
        return userFriends;
    }

    public Set<User> getCommonFriends(long userId1, long userId2) {
        User user1 = userStorage.getById(userId1);
        User user2 = userStorage.getById(userId2);
        Set<Long> commonFriendsIdsSet = new HashSet<>(user1.getFriends());
        commonFriendsIdsSet.retainAll(user2.getFriends());
        Set<User> commonFriendsSet = new HashSet<>();
        for (Long commonFriendId : commonFriendsIdsSet) {
            commonFriendsSet.add(userStorage.getById(commonFriendId));
        }
        return commonFriendsSet;
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public User save(User user) {
        validationService.validateNewData(user);
        return userStorage.save(user);
    }

    public User update(User user) {
        validationService.validateNewData(user);
        return userStorage.update(user);
    }

}



