package io.aweris.roo.api;

import io.aweris.roo.BaseIT;
import io.aweris.roo.domain.Customer;
import io.aweris.roo.domain.CustomerRepository;
import io.aweris.roo.domain.RoomOccupancy;
import io.reactivex.Flowable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

public class OccupancyServiceIT extends BaseIT {

    @Autowired
    private OccupancyService service;

    private static BigDecimal testLimit = BigDecimal.valueOf(100);

    @Test
    public void test_scenario_1() {
        service.query(3, 3, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(2)
               .assertValues(BigDecimal.valueOf(738), BigDecimal.valueOf(167))
               .awaitTerminalEvent();
    }

    @Test
    public void test_scenario_2() {
        service.query(7, 5, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(2)
               .assertValues(BigDecimal.valueOf(1054), BigDecimal.valueOf(189))
               .awaitTerminalEvent();
    }

    @Test
    public void test_scenario_3() {
        service.query(2, 7, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(2)
               .assertValues(BigDecimal.valueOf(583), BigDecimal.valueOf(189))
               .awaitTerminalEvent();
    }

    @Test
    public void test_scenario_4() {
        service.query(7, 1, testLimit)
               .map(RoomOccupancy::getTotalPayment)
               .test()
               .awaitCount(2)
               .assertValues(BigDecimal.valueOf(1153), BigDecimal.valueOf(45))
               .awaitTerminalEvent();
    }
}