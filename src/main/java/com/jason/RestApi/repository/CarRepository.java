package com.jason.RestApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jason.RestApi.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
