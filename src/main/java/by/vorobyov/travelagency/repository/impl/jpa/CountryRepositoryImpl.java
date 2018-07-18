package by.vorobyov.travelagency.repository.impl.jpa;

import by.vorobyov.travelagency.domain.Country;
import by.vorobyov.travelagency.repository.CountryRepository;
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
public class CountryRepositoryImpl implements CountryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Country> readAll() {
        String readAllCountriesQuery = "readAllCountriesQuery";
        Query query = entityManager.createNamedQuery(readAllCountriesQuery, Country.class);
        return query.getResultList();
    }

    @Override
    public boolean add(Country entity) {
        String createCountrySql = "INSERT INTO country (name) VALUES (?);";
        entityManager.joinTransaction();
        Query query = entityManager.createNativeQuery(createCountrySql, Country.class);
        query.setParameter(1, entity.getName());
        return query.executeUpdate() == 1;
    }

    @Override
    public Optional<Country> getEntity(long entityId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> criteria = criteriaBuilder.createQuery(Country.class);
        Root<Country> root = criteria.from(Country.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), entityId));
        try {
            return Optional.of(entityManager.createQuery(criteria).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Country entity) {
        String updateCountryHql = "UPDATE Country c SET c.name = :name WHERE c.id = :id";
        entityManager.joinTransaction();
        Query query = entityManager.createQuery(updateCountryHql);
        query.setParameter("name", entity.getName());
        query.setParameter("id", entity.getId());
        return query.executeUpdate() == 1;
    }

    @Override
    public boolean delete(long entityId) {
        String deleteCountrySql = "DELETE FROM country WHERE id = ?;";
        entityManager.joinTransaction();
        Query query = entityManager.createNativeQuery(deleteCountrySql);
        query.setParameter(1, entityId);
        return query.executeUpdate() == 1;
    }
}
