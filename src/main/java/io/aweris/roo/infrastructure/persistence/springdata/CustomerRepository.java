package io.aweris.roo.infrastructure.persistence.springdata;

import io.aweris.roo.domain.Customer;
import io.reactivex.Flowable;
import org.springframework.data.repository.reactive.RxJava2SortingRepository;

import java.math.BigDecimal;

public interface CustomerRepository extends RxJava2SortingRepository<Customer, Long> {

    Flowable<Customer> findByPaymentGreaterThanEqualOrderByPaymentDesc(BigDecimal payment);

    Flowable<Customer> findByPaymentLessThanOrderByPaymentDesc(BigDecimal payment);
}
