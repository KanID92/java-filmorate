package ru.yandex.practicum.filmorate.repository.feed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.repository.feed.mapper.FeedRowMapper;

import java.util.Collection;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcFeedRepository implements FeedRepository {
    private final FeedRowMapper feedRowMapper;
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Collection<Feed> getAllByUser(long userId) {
        String sql = "SELECT * FROM FEEDS " +
                "LEFT JOIN PUBLIC.EVENT_TYPES ET on ET.TYPES_ID = FEEDS.EVENT_TYPE " +
                "LEFT JOIN PUBLIC.EVENT_OPERATIONS EO on EO.OPERATION_ID = FEEDS.OPERATION " +
                "WHERE USER_ID = :userId";
        log.info("==> Get /feed Запрос ленты новосте юзера {} ", userId);
        return jdbc.query(sql, Map.of("userId", userId), feedRowMapper);
    }

    @Override
    public void add(Feed feed) {
        String sql = "INSERT INTO FEEDS (USER_ID, EVENT_TYPE, OPERATION, ENTITY_ID) " +
                "VALUES (:userId, " +
                "(SELECT TYPES_ID FROM EVENT_TYPES WHERE EVENT_TYPES.NAME = :eventType), " +
                "(SELECT EVENT_OPERATIONS.OPERATION_ID FROM EVENT_OPERATIONS WHERE EVENT_OPERATIONS.NAME = :operation), " +
                ":entityId)";
        Map<String, Object> params = Map.of("userId", feed.getUserId(),
                "eventType", feed.getEventType().toString(),
                "operation", feed.getOperation().toString(),
                "entityId", feed.getEntityId());
        jdbc.update(sql, params);
        log.info("==> PUT /feed Добвалена новость - {} ", feed);
    }
}
