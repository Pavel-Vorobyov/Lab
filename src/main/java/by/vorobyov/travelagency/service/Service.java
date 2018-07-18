package by.vorobyov.travelagency.service;

import by.vorobyov.travelagency.repository.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Estimate basic manipulations with domain.
 *
 * @param <T> some entity which suppose to be transferred.
 */
public interface Service<T> {

    /**
     * Method without arguments appeals to {@link Repository repository}
     * to get all domain objects stored there.
     *
     * @return  all domain objects type {@link T T} contained in {@link Repository
     *          repository}.
     */
    @Transactional(readOnly = true)
    List<T> readAll();

    /**
     * Calls add method in {@link Repository repository} to add the entity.
     *
     * @param entity  some entity type {@link T T} which supposed to be added.
     * @return        true in case entity was added successful, false in other.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean add(T entity);

    /**
     * Calls {@link Repository repository} get method, transmitting
     * entityId to get requested object.
     *
     * @param entityId  ID of the entity which supposed to be returned.
     * @return          entity which has entityId.
     */
    @Transactional(readOnly = true)
    Optional<T> getEntity(Integer entityId);

    /**
     * Transmits some domain object to {@link Repository repository} to updating.
     *
     * @param entity  some domain object which supposed to be updated.
     * @return        true in case the entity was updated successful, false in other.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean update(T entity);

    /**
     * Calls some deleting method of {@link Repository repository} transmitting
     * domain objects ID to deleting.
     *
     * @param entityId  ID of some domain object which supposed to be deleted.
     * @return          true in case object was deleted successful, false in others.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    boolean delete(Integer entityId);

}
