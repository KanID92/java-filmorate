package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.friend.FriendRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.service.validation.ValidationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseUserService implements UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final FriendRepository friendRepository;
    private final FilmRepository filmRepository;

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

    @Override
    public List<Film> recommendFilms(long userId) {
        User user = userRepository.getById(userId).orElseThrow(
                () -> new NotFoundException("Пользователь с данным id=" + userId + " не найден")
        );
        Set<Long> likes = userRepository.getAllLikes(userId);

        List<User> allUsers = (List<User>) userRepository.getAll();
        List<User> usersMaxIntersections = new ArrayList<>();
        for (User u : allUsers) {
            if (user.equals(u))
                continue;
            Set<Long> uLikes = userRepository.getAllLikes(u.getId());
            for (long like : uLikes) {
                if (likes.contains(like)) {
                    usersMaxIntersections.add(u);
                    break;
                }
            }
        }

        List<Film> recommended = new ArrayList<>();
        for (User u : usersMaxIntersections) {
            Set<Long> userLikes = userRepository.getAllLikes(u.getId());
            for (long like : userLikes) {
                if (!likes.contains(like)) {
                    recommended.add(filmRepository.getById(like).orElseThrow(() -> new NotFoundException("film with id " + like + " not found")));
                }
            }
        }

        return recommended;
    }

    public List<Long> findAllFilmLikes(long userId) {
        return userRepository.getAllLikes(userId).stream().toList();
    }
}



