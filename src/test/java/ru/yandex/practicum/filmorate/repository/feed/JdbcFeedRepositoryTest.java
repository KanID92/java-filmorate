package ru.yandex.practicum.filmorate.repository.feed;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.EventOperation;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.repository.feed.mapper.FeedRowMapper;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcFeedRepository.class, FeedRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JdbcFeedRepositoryTest {
    private final JdbcFeedRepository feedRepository;

    @Test
    void getAllByUser() {
        List<Feed> expectedFeeds = getFeeds();
        Collection<Feed> actualFeeds = feedRepository.getAllByUser(1);
        assertThat(actualFeeds)
                .isNotEmpty()
                .hasSize(2)
                .isEqualTo(expectedFeeds);
    }

    @Test
    void add() {
        Feed feed = getFeed();
        feedRepository.add(feed);
        List<Feed> allByUser = (List<Feed>) feedRepository.getAllByUser(feed.getUserId());
        assertThat(allByUser)
                .isNotEmpty()
                .hasSize(1);
        Feed actual = allByUser.getFirst();
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(actual);
    }

    private Feed getFeed() {
        Feed feed = new Feed();
        feed.setUserId(2);
        feed.setEventType(EventType.LIKE);
        feed.setEntityId(2);
        feed.setOperation(EventOperation.ADD);
        feed.setTimestamp(Date.from(Instant.now()));
        return feed;
    }

    private List<Feed> getFeeds() {
        Feed feed1 = new Feed();
        feed1.setUserId(1);
        feed1.setId(1);
        feed1.setEventType(EventType.LIKE);
        feed1.setEntityId(1);
        feed1.setOperation(EventOperation.ADD);
        feed1.setTimestamp(Date.from(Instant.now()));
        Feed feed2 = new Feed();
        feed2.setUserId(1);
        feed2.setId(2);
        feed2.setEventType(EventType.FRIEND);
        feed2.setEntityId(2);
        feed2.setOperation(EventOperation.ADD);
        feed2.setTimestamp(Date.from(Instant.now()));
        return List.of(feed1, feed2);
    }
}