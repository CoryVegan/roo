package io.aweris.roo.infrastructure.persistence;

import io.aweris.roo.domain.Customer;
import org.springframework.data.repository.reactive.RxJava2SortingRepository;

public interface CustomerRepository extends RxJava2SortingRepository<Customer, Long> {

}
