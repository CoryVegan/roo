package io.aweris.roo.infrastructure.rest;


import io.aweris.roo.api.CustomerService;
import io.aweris.roo.api.SaveCustomerCommand;
import io.aweris.roo.domain.Customer;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import static io.aweris.roo.infrastructure.utlis.CustomerUtils.customerCustomer;


@RestController
@Validated
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PutMapping
    public Single<Customer> saveCustomer(@Valid @RequestBody SaveCustomerCommand customer) {
        return service.save(new Customer(customer.getId(), customer.getPayment()));
    }

    @PostMapping
    public Flowable<Customer> importPayments(@Valid @NotNull @RequestBody List<BigDecimal> payments) {
        return service.saveAll(customerCustomer(payments));
    }

    @GetMapping
    public Flowable<Customer> findCustomers() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public Completable deleteById(@PathVariable @Valid @Min(1) Long id) {
        return service.deleteByID(id);
    }

    @DeleteMapping
    public Completable deleteAll() {
        return service.deleteAll();
    }


}
