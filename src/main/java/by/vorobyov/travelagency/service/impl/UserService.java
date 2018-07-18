package by.vorobyov.travelagency.service.impl;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.domain.User;
import by.vorobyov.travelagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements by.vorobyov.travelagency.service.UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> readAll() {
        return userRepository.readAll();
    }

    @Override
    public boolean add(User entity) {
        return userRepository.add(entity);
    }

    @Override
    public Optional<User> getEntity(Integer entityId) {
        return userRepository.getEntity(entityId);
    }

    @Override
    public boolean update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public boolean delete(Integer entityId) {
        return userRepository.delete(entityId);
    }

    @Override
    public boolean addTour(long userId, long tourId) {
        return userRepository.addTour(userId, tourId);
    }

    @Override
    public boolean deleteTour(long userId, long tourId) {
        return userRepository.deleteTour(userId, tourId);
    }

    @Override
    public List<Tour> tourList(long userId) {
        return userRepository.tourList(userId);
    }
}
