package io.aweris.roo.infrastructure.rest;

import io.aweris.roo.api.OccupancyService;
import io.aweris.roo.domain.Customer;
import io.aweris.roo.domain.RoomOccupancy;
import io.aweris.roo.infrastructure.rest.error.ApiError;
import io.reactivex.Flowable;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;


@WebFluxTest(OccupancyController.class)
public class OccupancyControllerTest extends BaseMvcTest {

    @MockBean
    OccupancyService service;

    private static BigDecimal testLimit = BigDecimal.valueOf(100);

    @Test
    public void should_get_room_occupancies() {
        RoomOccupancy premium = RoomOccupancy.premium()
                                             .add(new Customer(1L, BigDecimal.valueOf(150)))
                                             .add(new Customer(2L, BigDecimal.valueOf(145)));

        RoomOccupancy economy = RoomOccupancy.economy()
                                             .add(new Customer(5L, BigDecimal.valueOf(12)));

        when(service.query(3, 3, testLimit)).thenReturn(Flowable.just(premium, economy));

        webTestClient.get()
                     .uri(builder -> builder.path("/api/occupancies")
                                            .queryParam("premium", 3)
                                            .queryParam("economy", 3)
                                            .build())
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .exchange()
                     .expectStatus().is2xxSuccessful()
                     .expectBodyList(RoomOccupancy.class)
                     .hasSize(2)
                     .contains(premium, economy);
    }

    @Test
    public void should_return_validation_error(){
        webTestClient.get()
                     .uri(builder -> builder.path("/api/occupancies")
                                            .queryParam("premium", -1)
                                            .queryParam("economy", 3)
                                            .build())
                     .header(HttpHeaders.CONTENT_TYPE, "application/json")
                     .exchange()
                     .expectStatus().is4xxClientError()
                     .expectBody(ApiError.class);
    }
}