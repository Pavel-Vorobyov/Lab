package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.domain.User;
import by.vorobyov.travelagency.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> readAll() {
        String readAllUsersQuery = "readAllUsersQuery";
        Query query = entityManager.createNamedQuery(readAllUsersQuery);

        return query.getResultList();
    }

    @Override
    public boolean add(User entity) {
        String createUserSql = "INSERT INTO \"user\" (login, password, name, surname) VALUES (?,?,?,?)";
        entityManager.joinTransaction();

        Query query = entityManager.createNativeQuery(createUserSql, User.class);
        query.setParameter(1, entity.getLogin());
        query.setParameter(2, entity.getPassword());
        query.setParameter(3, entity.getName());
        query.setParameter(4, entity.getSurname());

        return query.executeUpdate() == 1;
    }

    @Override
    public Optional<User> getEntity(long entityId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), entityId));

        try {
            return Optional.of(entityManager.createQuery(criteria).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(User entity) {
        String updateUserHql = "UPDATE User u SET u.login = :login, u.password = :password" +
                ", u.name = :name, u.surname = :surname WHERE u.id = :id";
        entityManager.joinTransaction();

        Query query = entityManager.createQuery(updateUserHql);
        query.setParameter("login", entity.getLogin());
        query.setParameter("password", entity.getPassword());
        query.setParameter("name", entity.getName());
        query.setParameter("surname", entity.getSurname());
        query.setParameter("id", entity.getId());

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean delete(long entityId) {
        String deleteUserSql = "DELETE FROM user WHERE id = ?";
        entityManager.joinTransaction();

        Query query = entityManager.createNativeQuery(deleteUserSql);
        query.setParameter(1, entityId);

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean addTour(long userId, long tourId) {
        return false;
    }

    @Override
    public boolean deleteTour(long userId, long tourId) {
        return false;
    }

    @Override
    public List<Tour> tourList(long userId) {
        return null;
    }
}
