package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Review;
import by.vorobyov.travelagency.repository.ReviewRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    private Review testReview;
    private by.vorobyov.travelagency.service.ReviewService reviewService;

    @Before
    public void setUp() {
        reviewRepository = Mockito.mock(by.vorobyov.travelagency.repository.ReviewRepository.class);
        reviewService = new by.vorobyov.travelagency.service.impl.ReviewService(reviewRepository);
        testReview = new Review(1,null,null,"Test review");

        Mockito.when(reviewRepository.readAll()).thenReturn(Collections.EMPTY_LIST);
        Mockito.when(reviewRepository.add(Mockito.any(Review.class))).thenReturn(true);
        Mockito.when(reviewRepository.getEntity(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(reviewRepository.update(Mockito.any(Review.class))).thenReturn(true);
        Mockito.when(reviewRepository.delete(Mockito.anyLong())).thenReturn(true);
    }

    @After
    public void tearDown() {
        reviewRepository = null;
        reviewService = null;
        testReview = null;
    }

    @Test
    public void readAll() {
        Assert.assertTrue(reviewService.readAll() == Collections.EMPTY_LIST);
    }

    @Test
    public void add() {
        Assert.assertTrue(reviewService.add(testReview));
    }

    @Test
    public void getEntity() {
        Assert.assertTrue(reviewService.getEntity(1).equals(Optional.empty()));
    }

    @Test
    public void update() {
        Assert.assertTrue(reviewService.update(testReview));
    }

    @Test
    public void delete() {
        Assert.assertTrue(reviewService.delete(1));
    }
}