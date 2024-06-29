package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {
    Review create(Review review);

    Review update(Review review);

    void delete(Long id);

    Review getById(Long id);

    List<Review> getAll(Long filmId, int count);

    List<Review> getAll(int count);

    void addLikeOrDislike(Long id, Long userId, boolean like);

    void removeLikeOrDislike(Long id, Long userId);

}
