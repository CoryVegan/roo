package io.aweris.roo.api;

import io.aweris.roo.BaseUnitTest;
import io.aweris.roo.domain.Customer;
import io.aweris.roo.domain.CustomerRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class CustomerServiceTest extends BaseUnitTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    public void should_save_customer() {
        var customer = new Customer(1L, BigDecimal.TEN);

        when(repository.save(customer)).thenReturn(Single.just(customer));

        service.save(customer)
               .test()
               .awaitCount(1)
               .assertValue(customer)
               .assertNoErrors()
               .awaitTerminalEvent();
    }

    @Test
    public void should_save_flowable_customers() {
        Customer[] customers = {new Customer(1L, BigDecimal.TEN), new Customer(2L, BigDecimal.ONE)};

        var flowable = Flowable.fromArray(customers);

        when(repository.saveAll(flowable)).thenReturn(flowable);

        service.saveAll(flowable)
               .test()
               .awaitCount(2)
               .assertValues(customers)
               .assertNoErrors()
               .awaitTerminalEvent();

    }

    @Test
    public void should_find_all_customers() {
        Customer[] customers = {new Customer(1L, BigDecimal.TEN), new Customer(2L, BigDecimal.ONE)};

        when(repository.findAll()).thenReturn(Flowable.fromArray(customers));

        service.findAll()
               .test()
               .awaitCount(2)
               .assertValues(customers)
               .awaitTerminalEvent();
    }
}