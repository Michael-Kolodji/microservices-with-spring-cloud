package com.mk.microservices.userservice.repository;

import com.mk.microservices.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
