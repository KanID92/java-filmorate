package ru.yandex.practicum.filmorate.repository.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review create(Review review);

    Review update(Review review);

    void delete(Long id);

    Optional<Review> getById(Long id);

    List<Review> getByFilmLimit(Long filmId, int count);

    List<Review> getByFilmLimit(int count);

    void addLike(Long id, Long userId);

    void addDislike(Long id, Long userId);

    void removeLike(Long id, Long userId);

    void removeDislike(Long id, Long userId);
}
