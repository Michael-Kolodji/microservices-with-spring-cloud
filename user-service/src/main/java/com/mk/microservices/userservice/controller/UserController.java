package com.mk.microservices.userservice.controller;

import com.mk.microservices.userservice.entity.User;
import com.mk.microservices.userservice.model.Bike;
import com.mk.microservices.userservice.model.Car;
import com.mk.microservices.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getCars(userId));
    }


    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getBikes(userId));
    }

    @PostMapping("/save-car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Long userId, @RequestBody Car car) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId, car));
    }

    @PostMapping("/save-bike/{userId}")
    public ResponseEntity<Bike> saveCar(@PathVariable Long userId, @RequestBody Bike bike) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId, bike));
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehcles(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }

}
