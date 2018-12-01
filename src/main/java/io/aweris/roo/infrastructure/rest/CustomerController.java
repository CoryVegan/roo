package io.aweris.roo.infrastructure.rest;


import io.aweris.roo.api.CustomerService;
import io.aweris.roo.domain.Customer;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static io.aweris.roo.infrastructure.utlis.CustomerUtils.customerCustomer;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PutMapping
    public Single<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        return service.save(customer);
    }

    @PostMapping
    public Flowable<Customer> importPayments(@Valid @RequestBody List<BigDecimal> payments) {
        return service.saveAll(customerCustomer(payments));
    }

    @GetMapping
    public Flowable<Customer> findCustomers() {
        return service.findAll();
    }
}
