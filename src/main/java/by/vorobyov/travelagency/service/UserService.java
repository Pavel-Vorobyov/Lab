package by.vorobyov.travelagency.service;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.domain.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *Contains some special methods for user service.
 *
 * @see Service Service
 */
public interface UserService extends Service<User> {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean addTour(long userId, long tourId);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean deleteTour(long userId, long tourId);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    List<Tour> tourList(long userId);
}
