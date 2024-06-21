package ru.yandex.practicum.filmorate.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.review.ReviewRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import ru.yandex.practicum.filmorate.service.validation.ValidationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ValidationService validationService;
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    @Override
    public Review create(Review review) {
        validationService.validateNewData(review);
        checkUser(review.getUserId());
        checkUser(review.getFilmId());
        reviewRepository.create(review);
        return review;
    }

    @Override
    public Review update(Review review) {
        validationService.validateNewData(review);
        checkUser(review.getUserId());
        checkUser(review.getFilmId());
        reviewRepository.update(review);
        return review;
    }

    @Override
    public void delete(Long id) {
        reviewRepository.delete(id);
    }

    @Override
    public Review getById(Long id) {
        return reviewRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Not found review with id: " + id));
    }

    @Override
    public List<Review> getAll(Long filmId, int count) {
        return reviewRepository.getByFilmLimit(filmId, count);
    }

    @Override
    public List<Review> getAll(int count) {
        return reviewRepository.getByFilmLimit(count);
    }

    @Override
    public void addLike(Long id, Long userId) {
        checkUser(userId);
        reviewRepository.addLike(id, userId);
    }

    @Override
    public void addDislike(Long id, Long userId) {
        checkUser(userId);
        reviewRepository.addDislike(id, userId);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        checkUser(userId);
        reviewRepository.removeLike(id, userId);
    }

    @Override
    public void removeDislike(Long id, Long userId) {
        checkUser(userId);
        reviewRepository.removeDislike(id, userId);
    }

    private void checkUser(Long userId) {
        userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + userId));
    }

    private void checkFim(Long userId) {
        filmRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Not found film with id: " + userId));
    }
}
