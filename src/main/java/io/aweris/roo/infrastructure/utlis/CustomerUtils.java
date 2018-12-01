package io.aweris.roo.infrastructure.utlis;

import io.aweris.roo.domain.Customer;
import io.reactivex.Flowable;

import java.math.BigDecimal;
import java.util.List;

import static io.reactivex.Flowable.*;

public class CustomerUtils {

    public static Flowable<Customer> convertToCustomer(BigDecimal[] payments) {
        return rangeLong(1, payments.length).zipWith(fromArray(payments), Customer::new);
    }

    public static Flowable<Customer> customerCustomer(List<BigDecimal> payments) {
        return rangeLong(1, payments.size()).zipWith(fromIterable(payments), Customer::new);
    }
}
