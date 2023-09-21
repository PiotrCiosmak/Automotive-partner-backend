package com.ciosmak.automotivepartner.statistic.service;

import com.ciosmak.automotivepartner.statistic.api.response.OverallStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.YearStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.statistic.support.StatisticsExceptionSupplier;
import com.ciosmak.automotivepartner.statistic.support.StatisticsMapper;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticsService
{
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
        User user = userRepository.findById(userId).orElseThrow(UserExceptionSupplier.userNotFound(userId));

        Statistics statistics = statisticsRepository.findByUserIdAndDate(userId, adjustedDate).orElseThrow(StatisticsExceptionSupplier.incorrectDate(user.getFirstName(), user.getLastName(), date.getMonthValue(), date.getYear()));

        return statisticsMapper.toStatisticsResponse(statistics);
    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }
    
    public YearStatisticsResponse findInSelectedYear(Year year)
    {
        Integer yearMileage = statisticsRepository.sumMileageByYear(year.getValue());
        BigDecimal yearLpg = statisticsRepository.sumLpgByYear(year.getValue());
        BigDecimal yearPetrol = statisticsRepository.sumPetrolByYear(year.getValue());

        checkIfYearIsCorrect(yearMileage, yearLpg, yearPetrol, year);

        return statisticsMapper.toYearStatisticsResponse(yearMileage, yearLpg, yearPetrol);
    }

    private void checkIfYearIsCorrect(Integer yearMileage, BigDecimal yearLpg, BigDecimal yearPetrol, Year year)
    {
        if (yearMileage == null || yearLpg == null || yearPetrol == null)
        {
            throw StatisticsExceptionSupplier.incorrectYear(year.getValue()).get();
        }
    }
}
