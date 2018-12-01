package io.aweris.roo.api;

import io.aweris.roo.domain.Customer;
import io.aweris.roo.domain.CustomerRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Single<Customer> save(Customer customer) {
        return repository.save(customer);
    }

    public Flowable<Customer> saveAll(Flowable<Customer> customers) {
        return repository.saveAll(customers);
    }

    public Flowable<Customer> findAll() {
        return repository.findAll();
    }
}
