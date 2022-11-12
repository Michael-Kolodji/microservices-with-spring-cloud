package com.mk.microservices.bikeservice.service;

import com.mk.microservices.bikeservice.entity.Bike;
import com.mk.microservices.bikeservice.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    public List<Bike> getAll() {
        return bikeRepository.findAll();
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }

    public List<Bike> byUserId(Long userId) {
        return bikeRepository.findByUserId(userId);
    }

}
