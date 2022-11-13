package com.mk.microservices.userservice.controller;

import com.mk.microservices.userservice.entity.User;
import com.mk.microservices.userservice.model.Bike;
import com.mk.microservices.userservice.model.Car;
import com.mk.microservices.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();

        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getCars(userId));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/save-car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Long userId, @RequestBody Car car) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId, car));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getBikes(userId));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/save-bike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable Long userId, @RequestBody Bike bike) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId, bike));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackAllVehicles")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }

    private ResponseEntity<List<Car>> fallBackGetCars(Long userId, RuntimeException e) {
        return new ResponseEntity("The user " + userId + " has the cars in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(Long userId, Car car, RuntimeException e) {
        return new ResponseEntity("The user " + userId + " has the car in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(Long userId, RuntimeException e) {
        return new ResponseEntity("The user " + userId + " has the bikes in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(Long userId, Bike bike, RuntimeException e) {
        return new ResponseEntity("The user " + userId + " has the bike in the workshop", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackAllVehicles(Long userId, RuntimeException e) {
        return new ResponseEntity("The user " + userId + " has the vehicles in the workshop", HttpStatus.OK);
    }

}
