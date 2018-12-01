package io.aweris.roo.infrastructure.persistence;

import io.aweris.roo.BaseIT;
import io.aweris.roo.domain.Customer;
import io.reactivex.Flowable;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

public class CustomerRepositoryTest extends BaseIT {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ReactiveMongoOperations operations;

    @Before
    public void setUp() {
        var collection = operations.collectionExists(Customer.class)
                                   .flatMap(exists -> exists ? operations.dropCollection(Customer.class) : Mono.just(exists))
                                   .then(operations.createCollection(Customer.class, CollectionOptions.empty()));

        StepVerifier.create(collection).expectNextCount(1).verifyComplete();

        var data = Flowable.just(new Customer(1L, BigDecimal.valueOf(23)), new Customer(2L, BigDecimal.valueOf(209)));

        repository.saveAll(data)
                  .test()
                  .awaitCount(2)
                  .assertNoErrors()
                  .awaitTerminalEvent();
    }

    @Test
    public void should_insert_new_customers() {
        var c = new Customer(3L, BigDecimal.TEN);

        var save = repository.save(c);

        save.test()
            .awaitCount(1)
            .assertValue(c)
            .assertNoErrors()
            .awaitTerminalEvent();

        save.toFlowable()
            .flatMap(s -> repository.findAll())
            .count()
            .test()
            .awaitCount(1)
            .assertValue(3L)
            .assertNoErrors()
            .awaitTerminalEvent();
    }

    @Test
    public void should_insert_flowable_customers() {
        var customers = Flowable.just(new Customer(3L, BigDecimal.TEN), new Customer(4L, BigDecimal.ONE));

        repository.saveAll(customers)
                  .lastElement()
                  .toFlowable()
                  .flatMap(s -> repository.findAll())
                  .count()
                  .test()
                  .awaitCount(1)
                  .assertValue(4L)
                  .assertNoErrors()
                  .awaitTerminalEvent();
    }

    @Test
    public void should_find_all_customers() {
        repository.findAll()
                  .test()
                  .awaitCount(2)
                  .assertValues(new Customer(1L, BigDecimal.valueOf(23)), new Customer(2L, BigDecimal.valueOf(209)))
                  .assertNoErrors()
                  .awaitTerminalEvent();
    }
}