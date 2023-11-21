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

    @Query("SELECT COUNT(a) FROM Availability a WHERE a.date = :date AND a.type = :type")
    Integer countByDateAndType(LocalDate date, Type type);

    List<Availability> findAllByDateAndType(LocalDate date, Type type);

    List<Availability> findAllByDateAndTypeAndIsUsedFalse(LocalDate date, Type type);

    @Query("SELECT a FROM Availability a WHERE a.isUsed = false AND a.date > CURRENT_DATE OR (a.date = CURRENT_DATE AND ((HOUR(CURRENT_TIME) < 6) OR (HOUR(CURRENT_TIME) >= 6 AND HOUR(CURRENT_TIME) < 18 AND a.type = 1)))")
    List<Availability> findAllByIsUsedFalseAndDateFuture();
}
