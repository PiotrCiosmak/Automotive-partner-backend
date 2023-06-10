package com.ciosmak.automotivepartner.car.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>
{
    Optional<Car> findByRegistrationNumber(String registrationNumber);

    List<Car> findAllByBlocked(Boolean isBlocked);
}
