package com.ciosmak.automotivepartner.statistic.repository;

import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long>
{
    Optional<Statistics> findByUserId(Long userId);

    Optional<Statistics> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("SELECT SUM(s.mileage) AS mileage, SUM(s.lpg) AS lpg, SUM(s.petrol) AS petrol FROM Statistics s WHERE YEAR(s.date) = :year")
    Map<String, BigDecimal> sumValuesByYear(Integer year);
}
