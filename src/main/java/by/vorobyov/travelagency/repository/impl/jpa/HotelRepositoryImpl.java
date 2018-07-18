package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.domain.Hotel;
import by.vorobyov.travelagency.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HotelRepositoryImpl implements HotelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hotel> readAll() {
        String readAllQuery = "readAllHotelQuery";
        Query query = entityManager.createNamedQuery(readAllQuery, Hotel.class);

        return query.getResultList();
    }

    @Override
    public boolean add(Hotel entity) {
        String createHotelQuery = "INSERT INTO hotel (name, phone, country_id, stars) VALUES (?,?,?,?)";
        entityManager.joinTransaction();

        Query query = entityManager.createNativeQuery(createHotelQuery, Hotel.class);
        query.setParameter(1, entity.getName());
        query.setParameter(2, entity.getPhone());
        query.setParameter(3, entity.getCountry().getId());
        query.setParameter(4, entity.getStars());

        return query.executeUpdate() == 1;
    }

    @Override
    public Optional<Hotel> getEntity(long entityId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteria = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> root = criteria.from(Hotel.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), entityId));
        try {
            return Optional.of(entityManager.createQuery(criteria).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Hotel entity) {
        String updateHotelQuery = "UPDATE Hotel h SET h.name = :name, h.phone = :phone" +
                ", h.country = :country, h.stars = :stars WHERE h.id = :id";
        entityManager.joinTransaction();
        Query query = entityManager.createQuery(updateHotelQuery);
        query.setParameter("name", entity.getName());
        query.setParameter("phone", entity.getPhone());
        query.setParameter("country", entity.getCountry());
        query.setParameter("stars", entity.getStars());
        query.setParameter("id", entity.getId());

        return query.executeUpdate() == 1;
    }

    @Override
    public boolean delete(long entityId) {
        return false;
    }
}
