package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mapper.UserExtractor;
import ru.yandex.practicum.filmorate.repository.mapper.UserRowMapper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);
    private final NamedParameterJdbcOperations jdbc;
    private final FriendRepository friendsRepository;

    @Override
    public Optional<User> getById(long userId) {
        final String sql1 = "SELECT * FROM USERS " +
                "LEFT JOIN FRIENDS ON USERS.USER_ID = FRIENDS.USER_ID " +
                "WHERE USERS.USER_ID = :userId";
        User user = jdbc.query(sql1, Map.of("userId", userId), new UserExtractor());
        return Optional.ofNullable(user);
    }

    @Override
    public Collection<User> getFriendsByID(Long userId) {
        final String sql = "SELECT * FROM USERS WHERE USER_ID = (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID = :userId)";
        return jdbc.query(sql, Map.of("userId", userId), new UserRowMapper());
    }


    @Override
    public User save(User user) {
        final String sql = "INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "VALUES (:email, :login, :name, :birthday)";

        GeneratedKeyHolder keyHolderUser = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());
        jdbc.update(sql, params, keyHolderUser);
        user.setId(keyHolderUser.getKeyAs(Long.class));
        log.info("В хранилище сохранен user с id = " + user.getId());
        return user;
    }

    @Override
    public void deleteById(Long userId) {
        final String sql1 = "DELETE FROM USERS WHERE user_id = :userId";
        jdbc.update(sql1,
                Map.of("userId", userId));
        //String sql2 = "DELETE FROM LIKES WHERE user_id = :userId";
        //jdbc.update(sql2,
        //        Map.of("userId", userId));
        //String sql3 = "DELETE FROM FRIEND WHERE user_id_adding = :userId OR user_id_confirming = :user_id";
        //jdbc.update(sql3,
        //        Map.of("userId", userId));
    }

    @Override
    public Collection<User> getAll() {
        final String sqlUsers = "SELECT * FROM USERS";
        Collection<User> users = jdbc.query(sqlUsers, new UserRowMapper());
        //for(User user : users){
        //    user.getConfirmedFriends().addAll(jdbcFriendRepository.getFriendsIds(user.getId(), true));
        //    user.getUnconfirmedFriends().addAll(jdbcFriendRepository.getFriendsIds(user.getId(), false));
        //}

        return users;
    }

    @Override
    public User update(User user) {
        final String sqlUpdate = "UPDATE USERS " +
                "SET EMAIL = :email, " +
                "LOGIN = :login, NAME = :username, " +
                "BIRTHDAY = :birthday " +
                "WHERE USER_ID = :userId";
        jdbc.update(sqlUpdate, Map.of("userId", user.getId(),
                "email", user.getEmail(),
                "login", user.getLogin(),
                "username", user.getName(),
                "birthday", user.getBirthday()));
        Optional<User> userFromDb = getById(user.getId());
        if (userFromDb.isPresent()) {
            userFromDb.get().setFrindsSet(
                    friendsRepository.getFriendsIds(user.getId()));
            return userFromDb.get();
        } else {
            throw new NotFoundException("После обновления, пользователь c id= " + user.getId() + " не найден");
        }

    }

    @Override
    public Collection<User> getCommonFriends(long userId1, long userId2) {
        final String sql = "SELECT * from USERS AS u WHERE USER_ID IN " +
                "(SELECT FRIEND_ID FROM USERS AS u JOIN FRIENDS AS f ON u.USER_ID = f.USER_ID WHERE u.USER_ID = :userId1)" +
                "AND USER_ID IN " +
                "(SELECT FRIEND_ID FROM USERS AS u JOIN FRIENDS AS f ON u.USER_ID = f.USER_ID WHERE u.USER_ID = :userId2)";
        return jdbc.query(sql, Map.of("userId1", userId1, "userId2", userId2), new UserRowMapper());
    }

}
