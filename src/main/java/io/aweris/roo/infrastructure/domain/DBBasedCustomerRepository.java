package io.aweris.roo.infrastructure.domain;

import io.aweris.roo.domain.Customer;
import io.aweris.roo.infrastructure.persistence.springdata.CustomerRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

@Service
public class DBBasedCustomerRepository implements io.aweris.roo.domain.CustomerRepository {

    private CustomerRepository repository;

    public DBBasedCustomerRepository(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Customer> save(Customer entity) {
        return repository.save(entity);
    }

    @Override
    public Flowable<Customer> saveAll(Flowable<Customer> entityStream) {
        return repository.saveAll(entityStream);
    }

    @Override
    public Flowable<Customer> findAll() {
        return repository.findAll();
    }
}
