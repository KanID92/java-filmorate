package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcFriendRepository implements FriendRepository {

    private final NamedParameterJdbcOperations jdbcFr;

    @Override
    public void add(long userId, long friendId) {
        String sql = "INSERT INTO friends (user_id, friend_id) " +
                "VALUES (:userId, :friendId)";
        jdbcFr.update(sql, Map.of("userId", userId,
                "friendId", friendId));
    }

//    @Override
//    public Optional<Boolean> getFriendsStatus(long userId1, long userId2) {
//        String sqlFrStatus = "SELECT isConfirmed FROM FRIENDS " +
//                "WHERE (USER_ID_ADDING = :userId1 AND USER_ID_CONFIRMING = :userId2) " +
//                    "OR (USER_ID_ADDING = :userId2 AND USER_ID_CONFIRMING = :userId1) ";
//        try {
//            return Optional.ofNullable(jdbcFr.queryForObject(sqlFrStatus, Map.of(
//                    "userId1", userId1,
//                    "userId2", userId2), Boolean.class));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }

//    @Override
//    public Collection<Long> getFriendsRequest(long userId) {
//        String sql = "Select user_id_adding from friends where user_id_confirming = :userId";
//        Map<String, Object> params = Map.of("userId", userId);
//        return jdbcFr.queryForList(sql,params, Long.class);
//    }

    @Override
    public void delete(long userId, long friendId) {
        String sql = "DELETE FROM FRIENDS " +
                "WHERE (user_id = :userId AND friend_id= :friendId)";
        jdbcFr.update(sql, Map.of(
                "userId", userId,
                "friendId", friendId));
    }

    @Override
    public Set<Long> getFriendsIds(long userId) {
        String sql1 =
                "SELECT friend_id FROM friends " +
                        "WHERE user_id = :userId";
        return new HashSet<>(jdbcFr.queryForList(sql1, Map.of(
                "userId", userId), Long.class));
    }
//        String sql1 =
//                        "SELECT user_id_confirming FROM friends " +
//                        "WHERE user_id_adding = :userId AND isConfirmed = :isConfirmed";
//        List<Long> list = Objects.requireNonNullElse(jdbcFr.queryForList(sql1, Map.of(
//                "userId", userId,
//                "isConfirmed", isConfirmed), Long.class), Collections.emptyList());
//
//        String sql2 =
//                "SELECT user_id_adding FROM friends " +
//                        "WHERE user_id_confirming = :userId AND isConfirmed = :isConfirmed";
//        List<Long> list2 = Objects.requireNonNullElse(jdbcFr.queryForList(sql2, Map.of(
//                "userId", userId,
//                "isConfirmed", isConfirmed), Long.class),Collections.emptyList());
//        list.addAll(list2);
//        return new HashSet<>(list);
//    }

}
