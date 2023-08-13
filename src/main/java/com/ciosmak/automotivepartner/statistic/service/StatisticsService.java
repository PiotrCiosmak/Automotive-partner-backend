package com.ciosmak.automotivepartner.statistic.service;

import com.ciosmak.automotivepartner.statistic.api.response.OverallStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.YearStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.statistic.support.StatisticsExceptionSupplier;
import com.ciosmak.automotivepartner.statistic.support.StatisticsMapper;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticsService
{
    //TODO po każdej zakończonej zmianie na bazie roku i miesiac updatowany jest rekord w tabeli (dodawane są przejchane km, spalony gaz i pb)
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    private final StatisticsMapper statisticsMapper;

    public OverallStatisticsResponse findOverall(Long userId)
    {
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        Optional<Statistics> statisticsCandidate = statisticsRepository.findByUserId(userId);
        if (statisticsCandidate.isEmpty())
        {
            return statisticsMapper.toEmptyOverallStatisticsResponse(userId);
        }
        return statisticsMapper.toOverallStatisticsResponse(statisticsCandidate.get());
    }

    public StatisticsResponse findInSelectedMonth(Long userId, LocalDate date)
    {
        LocalDate adjustedDate = adjustDate(date);
        userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        Statistics statistics = statisticsRepository.findByUserIdAndDate(userId, adjustedDate).orElseThrow(StatisticsExceptionSupplier.IncorrectDate());

        return statisticsMapper.toStatisticsResponse(statistics);

    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }

    public YearStatisticsResponse findInSelectedYear(Year year)
    {
        Map<String, BigDecimal> yearStatistics = statisticsRepository.sumValuesByYear(year.getValue());

        checkIfYearIsCorrect(yearStatistics);

        yearStatistics.putIfAbsent("mileage", BigDecimal.ZERO);

        yearStatistics.putIfAbsent("lpg", BigDecimal.ZERO);

        yearStatistics.putIfAbsent("lpg", BigDecimal.ZERO);

        return statisticsMapper.toYearStatisticsResponse(yearStatistics);
    }

    private void checkIfYearIsCorrect(Map<String, BigDecimal> yearStatistics)
    {
        if (yearStatistics.get("mileage") == null || yearStatistics.get("lpg") == null || yearStatistics.get("petrol") == null)
        {
            throw StatisticsExceptionSupplier.IncorrectDate().get();
        }
    }
}
