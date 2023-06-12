package com.ciosmak.automotivepartner.availability.repository;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>
{
    Optional<Availability> findByUser_IdAndDate(Long userId, LocalDate date);
}
