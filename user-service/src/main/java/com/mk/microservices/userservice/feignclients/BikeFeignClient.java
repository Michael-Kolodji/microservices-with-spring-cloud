package com.mk.microservices.userservice.feignclients;

import com.mk.microservices.userservice.model.Bike;
import com.mk.microservices.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service", url = "http://localhost:8003", path = "/bike")
public interface BikeFeignClient {

    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping("/by-user/{id}")
    List<Bike> getBikes(@PathVariable Long id);

}
