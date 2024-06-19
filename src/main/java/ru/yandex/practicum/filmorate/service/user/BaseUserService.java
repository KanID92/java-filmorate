package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friend.FriendRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.service.validation.ValidationService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BaseUserService implements UserService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

    private final FriendRepository friendRepository;

    @Override
    public User getById(long userId) {
        return userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + userId + " не найден"));
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User create(User user) {
        validationService.validateNewData(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        userRepository.getById(user.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + user.getId() + " не найден"));
        validationService.validateNewData(user);
        return userRepository.update(user);
    }

    @Override
    public void deleteById(long userId) {
        userRepository.deleteById(userId);
    }


    @Override
    public void addFriend(long userId, long friendId) {

        userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id = " + userId + " не найден"));
        userRepository.getById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id = " + friendId + " не найден"));

        friendRepository.add(userId, friendId);

    }

    @Override
    public void deleteFriend(long userId, long friendId) {

        User user1 = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + userId + " не найден"));
        User user2 = userRepository.getById(friendId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + friendId + " не найден"));

        friendRepository.delete(userId, friendId);

    }

    @Override
    public Collection<Long> getFriendsIds(long userId) {
        return friendRepository.getFriendsIds(userId);
    }


    @Override
    public Collection<User> getFriendsById(long userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id = " + userId + " не найден"));
        return userRepository.getFriendsByID(userId);
    }


    @Override
    public Collection<User> getCommonFriends(long userId1, long userId2) {

        userRepository.getById(userId1)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + userId1 + " не найден"));
        userRepository.getById(userId2)
                .orElseThrow(() -> new NotFoundException("Пользователь с данным id=" + userId2 + " не найден"));

        return userRepository.getCommonFriends(userId1, userId2);

    }


}



