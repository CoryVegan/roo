package io.aweris.roo.domain;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.math.BigDecimal;

public interface CustomerRepository {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity.
     */
    Single<Customer> save(Customer entity);

    /**
     * Saves all given entities.
     *
     * @param entityStream must not be {@literal null}.
     * @return the saved entities.
     * @throws IllegalArgumentException in case the given {@code Publisher} is {@literal null}.
     */
    Flowable<Customer> saveAll(Flowable<Customer> entityStream);

    /**
     * Returns all instances of the type.
     *
     * @return all entities.
     */
    Flowable<Customer> findAll();

    /**
     * Find customers willing to pay more than given price
     *
     * @param payment must not be {@literal null}.
     * @return entities has payment greater than given Payment
     */
    Flowable<Customer> findByPaymentGreaterThan(BigDecimal payment);

    /**
     * Find customers willing to pay less than or equal given price
     *
     * @param payment must not be {@literal null}.
     * @return entities has payment greater than given Payment
     */
    Flowable<Customer> findByPaymentLessThan(BigDecimal payment);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}.
     */
    Completable deleteById(Long id);

    /**
     * Deletes all entities managed by the repository.
     */
    Completable deleteAll();
}
