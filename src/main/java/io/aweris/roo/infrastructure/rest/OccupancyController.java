package io.aweris.roo.infrastructure.rest;


import io.aweris.roo.api.OccupancyService;
import io.aweris.roo.domain.RoomOccupancy;
import io.reactivex.Flowable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/occupancies")
public class OccupancyController {

    private OccupancyService service;

    public OccupancyController(OccupancyService service) {
        this.service = service;
    }

    @GetMapping
    public Flowable<RoomOccupancy> query(@RequestParam long premium, @RequestParam long economy) {
        return service.query(premium, economy, BigDecimal.valueOf(100));
    }
}
