package io.aweris.roo.infrastructure.rest;


import io.aweris.roo.api.OccupancyQuery;
import io.aweris.roo.api.OccupancyService;
import io.aweris.roo.domain.RoomOccupancy;
import io.reactivex.Flowable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@Validated
@RequestMapping("/api/occupancies")
public class OccupancyController {

    private OccupancyService service;

    public OccupancyController(OccupancyService service) {
        this.service = service;
    }

    @GetMapping
    public Flowable<RoomOccupancy> query(@Valid OccupancyQuery query) {
        return service.query(query.getPremium(), query.getEconomy(), BigDecimal.valueOf(100));
    }
}
