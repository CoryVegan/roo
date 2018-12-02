package io.aweris.roo.infrastructure.rest;

import io.aweris.roo.api.CustomerService;
import io.aweris.roo.domain.Customer;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebFluxTest(CustomerController.class)
public class CustomerControllerTest extends BaseMvcTest {

    @MockBean
    CustomerService service;

    @Test
    public void should_save_new_customer() {
        var customer = new Customer(1L, BigDecimal.valueOf(100));

        when(service.save(customer)).thenReturn(Single.just(customer));

        webTestClient.put()
                     .uri("/api/customers")
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .body(Mono.just(customer), Customer.class)
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectBody().json("{ id: 1,  payment: 100}");
    }

    @Test
    public void when_payment_invalid_should_return_error() {
        var customer = new Customer(1L, BigDecimal.valueOf(-1));

        when(service.save(customer)).thenReturn(Single.just(customer));

        webTestClient.put()
                     .uri("/api/customers")
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .body(Mono.just(customer), Customer.class)
                     .exchange()
                     .expectStatus().is4xxClientError();
    }


    @Test
    public void when_id_invalid_should_return_error() {
        var customer = new Customer(-1L, BigDecimal.valueOf(1));

        when(service.save(customer)).thenReturn(Single.just(customer));

        webTestClient.put()
                     .uri("/api/customers")
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .body(Mono.just(customer), Customer.class)
                     .exchange()
                     .expectStatus().is4xxClientError();
    }


    @Test
    public void should_import_all_payments() {
        Flowable<BigDecimal> payments = Flowable.just(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30));

        var customers = Flowable.just(
                new Customer(1L, BigDecimal.valueOf(10)),
                new Customer(2L, BigDecimal.valueOf(20)),
                new Customer(3L, BigDecimal.valueOf(30))
        );

        when(service.saveAll(any())).thenReturn(customers);

        webTestClient.post()
                     .uri("/api/customers")
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .body(payments, BigDecimal.class)
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectBodyList(Customer.class).hasSize(3).contains(new Customer(1L, BigDecimal.TEN));
    }

    @Test
    public void should_return_all_customers() {
        when(service.findAll()).thenReturn(Flowable.just(new Customer(1L, BigDecimal.ONE), new Customer(2L, BigDecimal.TEN)));

        webTestClient.get()
                     .uri("/api/customers")
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectBody().json("[{id:1, payment:1},{id:2, payment:10}]");
    }
}