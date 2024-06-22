package ru.yandex.practicum.filmorate.repository.review;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.repository.review.mapper.ReviewRowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({JdbcReviewRepository.class, ReviewRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JdbcReviewRepositoryTest {
    private final JdbcReviewRepository reviewRepository;
    private final ReviewRowMapper reviewRowMapper;

    private static final Long TEST_REVIEW_01_ID = 1L;
    private static final Long TEST_REVIEW_02_ID = 2L;
    private static final Long TEST_REVIEW_03_ID = 3L;
    private static final Long TEST_REVIEW_04_ID = 4L;
    private static final Long TEST_FILM_01_ID = 1L;

    @Test
    void create() {
        Review review = reviewRepository.create(getReview());
        Optional<Review> optionalReview = reviewRepository.getById(review.getId());
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
    }

    @Test
    void update() {
        Review review = getReview();
        review.setId(TEST_REVIEW_01_ID);
        review.setContent("updated content");
        review.setUseful(3);
        reviewRepository.update(review);
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
        review.setId(999L);
        assertThatThrownBy(() -> reviewRepository.update(review))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Review not found");
    }

    @Test
    void delete() {
        reviewRepository.delete(TEST_REVIEW_01_ID);
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isEmpty();
    }

    @Test
    void getById() {
        Review review = getReview();
        review.setId(TEST_REVIEW_01_ID);
        review.setUseful(3);
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
        Optional<Review> notExistReview = reviewRepository.getById(999L);
        assertThat(notExistReview)
                .isEmpty();
    }

    @Test
    void getByFilmLimit() {
        List<Review> reviewsLimit = reviewRepository.getByFilmLimit(TEST_FILM_01_ID, 2);
        List<Review> expectedReviewsLimit = getReviewsLimit();
        assertThat(reviewsLimit)
                .isNotEmpty()
                .isEqualTo(expectedReviewsLimit);
        List<Review> expectedAllReviews = getReviews();
        List<Review> actualAllReviews = reviewRepository.getByFilmLimit(4);
        assertThat(actualAllReviews)
                .isNotEmpty()
                .isEqualTo(expectedAllReviews);
    }

    @Test
    void addLike() {
        Review review = getReview();
        review.setId(TEST_REVIEW_01_ID);
        review.setUseful(4);
        reviewRepository.addLike(TEST_REVIEW_01_ID, review.getUserId());
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
    }

    @Test
    void addDislike() {
        Review review = getReview();
        review.setId(TEST_REVIEW_01_ID);
        review.setUseful(2);
        reviewRepository.addDislike(TEST_REVIEW_01_ID, 1L);
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
    }

    @Test
    void removeLike() {
        Review review = getReview();
        review.setId(TEST_REVIEW_01_ID);
        review.setUseful(2);
        reviewRepository.removeLike(TEST_REVIEW_01_ID, 3L);
        Optional<Review> optionalReview = reviewRepository.getById(TEST_REVIEW_01_ID);
        assertThat(optionalReview)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(review);
    }


    private Review getReview() {
        Review review = new Review();
        review.setContent("test1");
        review.setUserId(1);
        review.setFilmId(1);
        review.setIsPositive(true);
        return review;
    }

    private List<Review> getReviewsLimit() {
        Review review01 = getReview();
        review01.setId(TEST_REVIEW_01_ID);
        review01.setUseful(3);
        Review review02 = getReview();
        review02.setId(TEST_REVIEW_02_ID);
        review02.setContent("test2");
        review02.setUserId(2);
        review02.setFilmId(1);
        review02.setUseful(-1);
        review02.setIsPositive(true);
        return List.of(review01, review02);
    }

    private List<Review> getReviews() {
        Review review01 = getReview();
        review01.setId(TEST_REVIEW_01_ID);
        review01.setUseful(3);
        Review review02 = getReview();
        review02.setId(TEST_REVIEW_02_ID);
        review02.setContent("test2");
        review02.setUserId(2);
        review02.setFilmId(1);
        review02.setUseful(-1);
        review02.setIsPositive(true);
        Review review03 = getReview();
        review03.setId(TEST_REVIEW_03_ID);
        review03.setContent("test3");
        review03.setUserId(3);
        review03.setFilmId(2);
        review03.setUseful(1);
        review03.setIsPositive(true);
        Review review04 = getReview();
        review04.setId(TEST_REVIEW_04_ID);
        review04.setContent("test4");
        review04.setUserId(4);
        review04.setFilmId(2);
        review04.setUseful(0);
        review04.setIsPositive(true);
        return List.of(review01, review03, review04, review02);
    }
}