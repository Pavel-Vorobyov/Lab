package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.domain.Tour;
import by.vorobyov.travelagency.repository.TourRepository;
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
public class TourRepositoryImpl implements TourRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tour> readAll() {
        String readAllToursQuery = "readAllToursQuery";
        Query query = entityManager.createNamedQuery(readAllToursQuery);

        return query.getResultList();
    }

    @Override
    public boolean add(Tour entity) {
        String addTourSql = "INSERT INTO tour (photo, date, duration, type_id" +
                ", description, coast, country_id, hotel_id) VALUES (?,?,?,?,?,?,?,?)";
        Query query = entityManager.createQuery(addTourSql, Tour.class);
        query.setParameter(1, entity.getPhoto());
        query.setParameter(2, entity.getDate());
        query.setParameter(3, entity.getDuration());
        query.setParameter(4, entity.getType().getId());
        query.setParameter(5, entity.getDescription());
        query.setParameter(6, entity.getCoast());
        query.setParameter(7, entity.getCountry().getId());
        query.setParameter(8, entity.getHotel().getId());

        return query.executeUpdate() == 1;
    }

    @Override
    public Optional<Tour> getEntity(long entityId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> criteria = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteria.from(Tour.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), entityId));

        try {
            return Optional.of(entityManager.createQuery(criteria).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Tour entity) {
        String updateTourHql = "UPDATE Tour t SET t.photo = :photo, t.date  = :date, t.duration = :duration" +
                ", t.country = :country, t.hotel = :hotel, t.type = :type, t.description = :description, t.coast = :coast";
        entityManager.joinTransaction();
        Query query = entityManager.createQuery(updateTourHql);
        query.setParameter("photo", entity.getPhoto());
        query.setParameter("date", entity.getDate());
        query.setParameter("duration", entity.getDescription());
        query.setParameter("country", entity.getCountry());
        query.setParameter("hotel", entity.getHotel());
        query.setParameter("type", entity.getType());
        query.setParameter("description", entity.getDescription());
        query.setParameter("coast", entity.getCoast());

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean delete(long entityId) {
        String deleteTourSql = "DELETE FROM tour WHERE id = ?";
        entityManager.joinTransaction();
        Query query = entityManager.createQuery(deleteTourSql);
        query.setParameter(1, entityId);

        return query.executeUpdate() == 1;
    }
}
