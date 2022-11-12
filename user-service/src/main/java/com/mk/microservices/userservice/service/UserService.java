package com.mk.microservices.userservice.service;

import com.mk.microservices.userservice.entity.User;
import com.mk.microservices.userservice.feignclients.BikeFeignClient;
import com.mk.microservices.userservice.feignclients.CarFeignClient;
import com.mk.microservices.userservice.model.Bike;
import com.mk.microservices.userservice.model.Car;
import com.mk.microservices.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final CarFeignClient carFeignClient;

    private final BikeFeignClient bikeFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(long userId) {
        return restTemplate.getForObject("http://localhost:8002/car/by-user/" + userId, List.class);
    }

    public List<Bike> getBikes(long userId) {
        return restTemplate.getForObject("http://localhost:8003/bike/by-user/" + userId, List.class);
    }

    public Car saveCar(Long userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(Long userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String, Object> getUserAndVehicles(Long userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Message", "user not found");
            return result;
        }

        result.put("User", user);

        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty()) {
            result.put("Cars", "car not found.");
        } else {
            result.put("Cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty()) {
            result.put("Bikes", "bike not found.");
        } else {
            result.put("Bikes", bikes);
        }

        return result;

    }

}
