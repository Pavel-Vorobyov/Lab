package by.vorobyov.travelagency.repository;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.domain.User;

import java.util.List;

/**
 *Contains some special methods for user repository.
 *
 * @see Repository Repository.
 */
public interface UserRepository extends Repository<User> {
    boolean addTour(long userId, long tourId);
    boolean deleteTour(long userId, long tourId);
    List<Tour> tourList(long userId);
}
