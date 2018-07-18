package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.domain.Review;
import by.vorobyov.travelagency.repository.ReviewRepository;
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
public class ReviewRepositoryImpl implements ReviewRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Review> readAll() {
        String readAllReviewsQuery = "readAllReviewsQuery";
        Query query = entityManager.createNamedQuery(readAllReviewsQuery, Review.class);

        return query.getResultList();
    }

    @Override
    public boolean add(Review entity) {
        String createReviewSql = "INSERT INTO review (tour_id, user_id, content) VALUES (?,?,?)";

        Query query = entityManager.createNativeQuery(createReviewSql, Review.class);
        query.setParameter(1, entity.getTour().getId());
        query.setParameter(2, entity.getUser().getId());
        query.setParameter(3, entity.getContent());

        return query.executeUpdate() == 1;
    }

    @Override
    public Optional<Review> getEntity(long entityId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), entityId));

        try {
            return Optional.of(entityManager.createQuery(criteria).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Review entity) {
        String updateReviewHql = "UPDATE Review r SET r.tour = :tour, r.user = :user" +
                ", r.content = :content WHERE r.id = :id";
        entityManager.joinTransaction();

        Query query = entityManager.createQuery(updateReviewHql);
        query.setParameter("tour", entity.getTour());
        query.setParameter("user", entity.getUser());
        query.setParameter("content", entity.getContent());
        query.setParameter("id", entity.getId());

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean delete(long entityId) {
        String deleteReviewSql = "DELETE FROM review WHERE id = ?";
        entityManager.joinTransaction();

        Query query = entityManager.createQuery(deleteReviewSql);
        query.setParameter(1, entityId);

        return query.executeUpdate() == 1;
    }
}
