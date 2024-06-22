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

    void addLike(Long id, Long userId);

    void addDislike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    void removeDislike(Long id, Long userId);
}
