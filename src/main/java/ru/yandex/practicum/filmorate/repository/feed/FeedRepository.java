package ru.yandex.practicum.filmorate.repository.feed;

import ru.yandex.practicum.filmorate.model.Feed;

import java.util.Collection;

public interface FeedRepository {
    Collection<Feed> getAllByUser(long userId);

    void add(Feed feed);
}
