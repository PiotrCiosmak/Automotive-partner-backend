package com.ciosmak.automotivepartner.statistic.service;

import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.statistic.support.StatisticsExceptionSupplier;
import com.ciosmak.automotivepartner.statistic.support.StatisticsMapper;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.YearMonth;

@AllArgsConstructor
@Service
public class StatisticsService
{
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;

    public StatisticsResponse findUserOverall(Long userId)
    {
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));
        Statistics statistics = statisticsRepository.findByUserId(userId).orElseThrow(StatisticsExceptionSupplier.userStatisticsNotExists(userId));
        // return statisticsMapper.toStatisticsResponse(statistics);
        return null;
    }

    public StatisticsResponse findUserInSelectedMonth(Long userId, YearMonth date)
    {
        return null;
    }

    public StatisticsResponse findInSelectedYear(Year year)
    {
        return null;
        // Statistics statistics = statisticsRepository.findByYear(year).orElseThrow(StatisticsExceptionSupplier.statisticsNotExists(year));
    }
}
