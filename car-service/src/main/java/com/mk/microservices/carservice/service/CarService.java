package com.mk.microservices.carservice.service;

import com.mk.microservices.carservice.entity.Car;
import com.mk.microservices.carservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> byUserId(Long userId) {
        return carRepository.findByUserId(userId);
    }

}
