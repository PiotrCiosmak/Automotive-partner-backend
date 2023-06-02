package com.ciosmak.automotivepartner.car.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>
{
}
