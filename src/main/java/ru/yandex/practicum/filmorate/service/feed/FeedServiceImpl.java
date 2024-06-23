package ru.yandex.practicum.filmorate.service.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.repository.feed.FeedRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;

    @Override
    public Collection<Feed> getFeeds(long userId) {
        return feedRepository.getAllByUser(userId);
    }
}
