package com.ciosmak.automotivepartner.statistic.repository;

import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long>
{
    List<Statistics> findByUserId(Long userId);

    Optional<Statistics> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("SELECT SUM(s.mileage) AS mileage FROM Statistics s WHERE YEAR(s.date) = :year")
    Integer sumMileageByYear(Integer year);

    @Query("SELECT SUM(s.lpg) AS mileage FROM Statistics s WHERE YEAR(s.date) = :year")
    BigDecimal sumLpgByYear(Integer year);

    @Query("SELECT SUM(s.petrol) AS mileage FROM Statistics s WHERE YEAR(s.date) = :year")
    BigDecimal sumPetrolByYear(Integer year);
}
