package dev.opafritz.logistiker.controller;

import dev.opafritz.logistiker.entities.Freight;
import dev.opafritz.logistiker.services.FreightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/freight")
public class FreightController {
    FreightService freightService;
    public FreightController(FreightService freightService){
        this.freightService = freightService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Freight>> getAll(){
        return ResponseEntity.ok(freightService.getAllFreights());
    }
}
