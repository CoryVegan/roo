package io.aweris.roo.domain;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
}
