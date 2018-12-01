package io.aweris.roo.api;

import io.aweris.roo.BaseUnitTest;
import io.aweris.roo.domain.Customer;
import io.aweris.roo.domain.CustomerRepository;
import io.aweris.roo.domain.RoomOccupancy;
import io.reactivex.Flowable;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class OccupancyServiceTest extends BaseUnitTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private OccupancyService service;

    private static BigDecimal testLimit = BigDecimal.valueOf(100);

    @Test
    public void should_return_occupancies_without_upgrades() {

        var pc = Flowable.just(
                new Customer(1L, BigDecimal.valueOf(150)),
                new Customer(2L, BigDecimal.valueOf(145))
        );

        when(repository.findByPaymentGreaterThan(testLimit)).thenReturn(pc);

        var ec = Flowable.just(
                new Customer(5L, BigDecimal.valueOf(12))
        );

        when(repository.findByPaymentLessThan(testLimit)).thenReturn(ec);

        service.query(2, 1, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(1)
               .assertValues(BigDecimal.valueOf(295), BigDecimal.valueOf(12))
               .awaitTerminalEvent();
    }

    @Test
    public void should_return_occupancies_with_upgrades(){
        var pc = Flowable.just(
                new Customer(1L, BigDecimal.valueOf(150)),
                new Customer(2L, BigDecimal.valueOf(145))
        );

        when(repository.findByPaymentGreaterThan(testLimit)).thenReturn(pc);

        var ec = Flowable.just(

                new Customer(4L, BigDecimal.valueOf(50)),
                new Customer(5L, BigDecimal.valueOf(12))
        );

        when(repository.findByPaymentLessThan(testLimit)).thenReturn(ec);

        service.query(3, 1, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(1)
               .assertValues(BigDecimal.valueOf(345), BigDecimal.valueOf(12))
               .awaitTerminalEvent();
    }

    @Test
    public void should_not_upgrade_when_all_customers_can_occupancy_in_echonomy(){
        var pc = Flowable.just(
                new Customer(1L, BigDecimal.valueOf(150)),
                new Customer(2L, BigDecimal.valueOf(145))
        );

        when(repository.findByPaymentGreaterThan(testLimit)).thenReturn(pc);

        var ec = Flowable.just(

                new Customer(4L, BigDecimal.valueOf(50)),
                new Customer(5L, BigDecimal.valueOf(12))
        );

        when(repository.findByPaymentLessThan(testLimit)).thenReturn(ec);

        service.query(3, 2, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(1)
               .assertValues(BigDecimal.valueOf(295), BigDecimal.valueOf(62))
               .awaitTerminalEvent();
    }

    @Test
    public void should_upgrade_only_premium_free_rooms(){
        var pc = Flowable.just(
                new Customer(1L, BigDecimal.valueOf(150)),
                new Customer(2L, BigDecimal.valueOf(145))
        );

        when(repository.findByPaymentGreaterThan(testLimit)).thenReturn(pc);

        var ec = Flowable.just(

                new Customer(4L, BigDecimal.valueOf(50)),
                new Customer(5L, BigDecimal.valueOf(40)),
                new Customer(5L, BigDecimal.valueOf(30)),
                new Customer(5L, BigDecimal.valueOf(12))
        );

        when(repository.findByPaymentLessThan(testLimit)).thenReturn(ec);

        service.query(3, 1, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(1)
               .assertValues(BigDecimal.valueOf(345), BigDecimal.valueOf(40))
               .awaitTerminalEvent();
    }
}