package ru.yandex.practicum.filmorate.service.feed;

import ru.yandex.practicum.filmorate.model.Feed;

import java.util.Collection;


public interface FeedService {
    Collection<Feed> getFeeds(long userId);
}
