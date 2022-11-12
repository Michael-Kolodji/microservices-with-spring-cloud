package com.mk.microservices.carservice.repository;

import com.mk.microservices.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByUserId(Long userId);

}
