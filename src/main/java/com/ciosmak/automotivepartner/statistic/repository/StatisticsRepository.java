package com.ciosmak.automotivepartner.statistic.repository;

import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long>
{
    Optional<Statistics> findByUserId(Long userId);

/*
    @Query("SELECT s.mileage, s.lpg, s.petrol FROM Statistics s WHERE s.monthAndYear >=")
    Optional<Statistics> findByYear(Year year)*/

}
