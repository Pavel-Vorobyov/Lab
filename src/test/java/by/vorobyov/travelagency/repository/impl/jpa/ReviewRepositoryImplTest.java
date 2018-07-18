package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.configuration.TestConfiguration;
import by.vorobyov.travelagency.domain.Review;
import by.vorobyov.travelagency.repository.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("jpa")
public class ReviewRepositoryImplTest {
    @Autowired
    private ReviewRepository reviewRepository;
    private Review testReview;

    @Before
    public void init() {
        testReview = new Review(1,null,null,"Test review");
    }

    @Test
    public void readAll() {
        assertFalse(reviewRepository.readAll().isEmpty());
    }

    @Test
    public void add() {
        reviewRepository.add(testReview);
        Optional<Review> actualReview = reviewRepository.readAll()
                .stream()
                .filter((o) -> o.getContent().equals(testReview.getContent()))
                .findAny();

        assertTrue(actualReview.isPresent());
    }

    @Test
    public void getEntity() {
        reviewRepository.add(testReview);
        Optional<Review> actualReview = reviewRepository.readAll()
                .stream()
                .filter((o) -> o.getContent().equals(testReview.getContent()))
                .findAny();
        Optional<Review> addedReview = reviewRepository.getEntity(actualReview.get().getId());

        assertTrue(addedReview.isPresent());
    }

    @Test
    public void update() {
        reviewRepository.add(testReview);
        Optional<Review> actualReview = reviewRepository.readAll()
                .stream()
                .filter((o) -> o.getContent().equals(testReview.getContent()))
                .findAny();
        if (!actualReview.isPresent()) {
            fail();
        } else {
            actualReview.get().setContent("Updated content");
            reviewRepository.update(actualReview.get());

            Optional<Review> resultReview = reviewRepository.readAll()
                    .stream()
                    .filter((o) -> o.getContent().equals(actualReview.get().getContent()))
                    .findAny();

            assertTrue(resultReview.isPresent());
        }
    }

    @Test
    public void delete() {
        reviewRepository.add(testReview);
        Optional<Review> actualReview = reviewRepository.readAll()
                .stream()
                .filter((o) -> o.getContent().equals(testReview.getContent()))
                .findAny();
        if (!actualReview.isPresent()) {
            fail();
        } else {
            reviewRepository.delete(actualReview.get().getId());
            actualReview = reviewRepository.getEntity(actualReview.get().getId());

            assertFalse(actualReview.isPresent());
        }
    }

}