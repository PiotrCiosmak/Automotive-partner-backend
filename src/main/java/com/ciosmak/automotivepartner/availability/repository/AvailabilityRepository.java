package com.ciosmak.automotivepartner.availability.repository;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.shift.support.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long>
{
    Optional<Availability> findByUser_IdAndDate(Long userId, LocalDate date);

    Optional<Availability> findByUser_IdAndDateAndType(Long userId, LocalDate date, Type type);

    @Query("SELECT COUNT(a) FROM Availability a WHERE a.date = :date AND a.type = :type")
    Integer countByDateAndType(LocalDate date, Type type);

    List<Availability> findAllByDateAndType(LocalDate date, Type type);
}
