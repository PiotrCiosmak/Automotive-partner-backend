package com.ciosmak.automotivepartner.car.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.shift.support.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>
{
    Optional<Car> findByRegistrationNumber(String registrationNumber);

    List<Car> findAllByIsBlocked(Boolean isBlocked);

    @Query("SELECT c.isBlocked FROM Car c WHERE c.id = :id")
    boolean isBlocked(@Param("id") Long id);

    @Query("SELECT c FROM Car c WHERE c.isBlocked = false AND NOT EXISTS" +
            "(SELECT s FROM Shift s WHERE s.car = c AND s.date = :shiftDate AND s.type = :shiftType)")
    List<Car> findAvailableCarsForShift(LocalDate shiftDate, Type shiftType);
}
