package com.ciosmak.automotivepartner.shift.repository;

import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.support.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>
{
    List<Shift> findAllByDate(LocalDate date);

    Optional<Shift> findByUser_IdAndDateAndType(Long userId, LocalDate date, Type type);

    List<Shift> findAllByDateAndType(LocalDate date, Type type);

    List<Shift> findAllByCarId(Long carId);

    Integer countByUser_IdAndIsDoneTrue(Long userId);

    @Query("SELECT SUM(s.lpg) FROM Shift s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month AND s.user.id = :userId")
    Optional<BigDecimal> getTotalLpgByUserAndYearAndMonth(Integer year, Integer month, Long userId);

    @Query("SELECT s FROM Shift s WHERE s.isCarAvailable=false  AND s.date > :date OR (s.date = :date AND ((HOUR(:date) < 6) OR (HOUR(:date) >= 6 AND HOUR(:date) < 18 AND s.type = 1)))")
    List<Shift> findByIsCarAvailableFalseAndDateGreaterThanEqual(LocalDateTime date);
    //TODO TEST SPRAWDZIĆ CZY DZIAŁA (SWAGGER FORMAT DATY, jesli nie to dać LocalDate i LocalTime osobno)

    @Query("SELECT s FROM Shift s WHERE s.isStarted = false AND s.isDone = false AND s.car.id = :carId AND s.date > CURRENT_DATE OR (s.date = CURRENT_DATE AND ((HOUR(CURRENT_TIME) < 6) OR (HOUR(CURRENT_TIME) >= 6 AND HOUR(CURRENT_TIME) < 18 AND s.type = 1)))")
    List<Shift> findUnstartedAndUndoneShifts(Long carId);

    @Query("SELECT s FROM Shift s WHERE s.isCarAvailable = false AND s.date > CURRENT_DATE OR (s.date = CURRENT_DATE AND ((HOUR(CURRENT_TIME) < 6) OR (HOUR(CURRENT_TIME) >= 6 AND HOUR(CURRENT_TIME) < 18 AND s.type = 1)))")
    List<Shift> findShiftsForCarAndDateFuture(Long carId);
}
