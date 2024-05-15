package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    private final ValidationService validationService;

    public User getById(long userId) {
        return validationService.validateUserExisting(userId, userStorage.getAll());
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
        Map<Long, User> allUsers = userStorage.getAll();
        validationService.validateUserExisting(userId, allUsers);
        Set<Long> userFriendsIds = userStorage.getById(userId).getFriends();
        Set<User> userFriends = new HashSet<>();

        for (Long userFriendId : userFriendsIds) {
            userFriends.add(allUsers.get(userFriendId));
        }
        return userFriends;
    }

    public Set<User> getCommonFriends(long userId1, long userId2) {
        Map<Long, User> allUsers = userStorage.getAll();
        User user1 = allUsers.get(userId1);
        User user2 = allUsers.get(userId2);
        Set<Long> commonFriendsIdsSet = new HashSet<>(user1.getFriends());
        commonFriendsIdsSet.retainAll(user2.getFriends());
        Set<User> commonFriendsSet = new HashSet<>();
        for (Long commonFriendId : commonFriendsIdsSet) {
            commonFriendsSet.add(allUsers.get(commonFriendId));
        }
        return commonFriendsSet;
    }

    public Map<Long, User> getAll() {
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



