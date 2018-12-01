package io.aweris.roo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class RoomOccupancy {

    private RoomType roomType;
    private BigDecimal totalPayment;
    private List<Customer> customers;

    public RoomOccupancy add(Customer customer) {
        totalPayment = totalPayment.add(customer.getPayment());
        customers.add(customer);
        return this;
    }

    public static RoomOccupancy premium() {
        return new RoomOccupancy(RoomType.PREMIUM, BigDecimal.ZERO, new ArrayList<>());
    }

    public static RoomOccupancy economy() {
        return new RoomOccupancy(RoomType.ECONOMY, BigDecimal.ZERO, new ArrayList<>());
    }

    enum RoomType {
        PREMIUM, ECONOMY
    }
}
