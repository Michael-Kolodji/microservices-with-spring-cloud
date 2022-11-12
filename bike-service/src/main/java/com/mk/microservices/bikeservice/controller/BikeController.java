package com.mk.microservices.bikeservice.controller;

import com.mk.microservices.bikeservice.entity.Bike;
import com.mk.microservices.bikeservice.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll() {
        List<Bike> bikes = bikeService.getAll();

        if(bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getCarById(@PathVariable Long id) {
        Bike bike = bikeService.getBikeById(id);

        if(bike == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {
        return ResponseEntity.ok(bikeService.save(bike));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable Long id) {
        List<Bike> bikes = bikeService.byUserId(id);

        if(bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bikes);
    }

}
