package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Review;
import by.vorobyov.travelagency.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements by.vorobyov.travelagency.service.ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> readAll() {
        return reviewRepository.readAll();
    }

    @Override
    public boolean add(Review entity) {
        return reviewRepository.add(entity);
    }

    @Override
    public Optional<Review> getEntity(Integer entityId) {
        return reviewRepository.getEntity(entityId);
    }

    @Override
    public boolean update(Review entity) {
        return reviewRepository.update(entity);
    }

    @Override
    public boolean delete(Integer entityId) {
        return reviewRepository.delete(entityId);
    }
}
