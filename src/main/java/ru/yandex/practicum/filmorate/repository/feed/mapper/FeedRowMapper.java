package ru.yandex.practicum.filmorate.repository.feed.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedRowMapper implements RowMapper<Feed> {
    @Override
    public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
        Feed feed = new Feed();
        feed.setId(rs.getInt("event_id"));
        feed.setTimestamp(rs.getDate("timestamp"));
        feed.setEventType(EventType.valueOf(rs.getString("EVENT_TYPES.name")));
        feed.setOperation(EventOperation.valueOf(rs.getString("EVENT_OPERATIONS.name")));
        feed.setUserId(rs.getInt("user_id"));
        feed.setEntityId(rs.getInt("entity_id"));
        return feed;
    }
}
