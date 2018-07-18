package by.vorobyov.travelagency.repository;

import by.vorobyov.travelagency.annotation.ExceptionType;
import by.vorobyov.travelagency.annotation.Loggable;
import javafx.beans.property.Property;

import javax.persistence.JoinColumn;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Estimate basic method of storing domain objects. Has the encapsulated collection
 * which stores all domain objects.
 *
 * @see java.util.Collection
 * @param <T> some entity which suppose to be stored.
 */
public interface Repository<T> {

    /**
     *@return  all domain object which contained in collection.
     */
    List<T> readAll();

    /**
     * @param entity  domain object which supposed to be stored.
     * @return        true - in case domain object was added to the collection successful,
     *                false - in other cases.
     */
    boolean add(T entity);

    /**
     * @param entityId  ID of the domain object which supposed to be returned.
     * @return          domain object which sored in the collection with entityId.
     */
    Optional<T> getEntity(long entityId);

    /**
     * @param entity    domain object which supposed to be updated.
     * @return          true - in case updating was successful, false - in other cases.
     */
    boolean update(T entity);

    /**
     * @param entityId  ID of the domain object which supposed to be deleted.
     * @return          true - in case the domain object was deleted successful, false - in other.
     */
    boolean delete(long entityId);

}
