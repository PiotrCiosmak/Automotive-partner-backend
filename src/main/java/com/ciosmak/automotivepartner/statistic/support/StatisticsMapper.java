package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.statistic.api.request.StatisticsRequest;
import com.ciosmak.automotivepartner.statistic.api.request.StatisticsUpdateRequest;
import com.ciosmak.automotivepartner.statistic.api.response.OverallStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.YearStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class StatisticsMapper
{
    private final UserRepository userRepository;

    public Statistics toStatistics(StatisticsRequest statisticsRequest)
    {
        return new Statistics(adjustDate(statisticsRequest.getDate()), statisticsRequest.getMileage(), statisticsRequest.getLpg(), statisticsRequest.getPetrol(), userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
    }

    public Statistics toStatistics(Statistics statistics, StatisticsRequest statisticsRequest)
    {
        statistics.setDate(adjustDate(statisticsRequest.getDate()));
        statistics.setMileage(statisticsRequest.getMileage());
        statistics.setLpg(statisticsRequest.getLpg());
        statistics.setPetrol(statisticsRequest.getPetrol());
        statistics.setUser(userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
        return statistics;
    }

    public StatisticsUpdateRequest toStatisticsUpdateRequest(Shift shift)
    {
        return new StatisticsUpdateRequest(shift.getEndMileage() - shift.getStartMileage(), shift.getLpg(), shift.getPetrol());
    }

    public StatisticsRequest toStatisticsRequest(Shift shift)
    {
        return new StatisticsRequest(adjustDate(shift.getDate()), shift.getEndMileage() - shift.getStartMileage(), shift.getLpg(), shift.getPetrol(), shift.getUser().getId());
    }

    public Statistics toStatistics(Statistics statistics, StatisticsUpdateRequest statisticsUpdateRequest)
    {
        statistics.setDate(adjustDate(statistics.getDate()));
        statistics.setMileage(statistics.getMileage() + statisticsUpdateRequest.getMileage());
        statistics.setLpg(statistics.getLpg().add(statisticsUpdateRequest.getLpg()));
        statistics.setPetrol(statistics.getPetrol().add(statisticsUpdateRequest.getPetrol()));
        return statistics;
    }

    public StatisticsResponse toStatisticsResponse(Statistics statistics)
    {
        return new StatisticsResponse(statistics.getId(), adjustDate(statistics.getDate()), statistics.getMileage(), statistics.getLpg(), statistics.getPetrol(), statistics.getUser().getId());
    }

    public OverallStatisticsResponse toOverallStatisticsResponse(List<Statistics> statisticsList)
    {
        Long userId = statisticsList.get(0).getUser().getId();
        Integer mileage = 0;
        BigDecimal lpg = BigDecimal.ZERO;
        BigDecimal petrol = BigDecimal.ZERO;

        for (var statistics : statisticsList)
        {
            mileage += statistics.getMileage();
            lpg = lpg.add(statistics.getLpg());
            petrol = petrol.add(statistics.getPetrol());
        }
        return new OverallStatisticsResponse(mileage, lpg, petrol, userId);
    }

    public OverallStatisticsResponse toEmptyOverallStatisticsResponse(Long userId)
    {
        return new OverallStatisticsResponse(0, BigDecimal.ZERO, BigDecimal.ZERO, userId);
    }

    public YearStatisticsResponse toYearStatisticsResponse(Integer mileage, BigDecimal lpg, BigDecimal petrol)
    {
        return new YearStatisticsResponse(mileage, lpg, petrol);
    }

    private LocalDate adjustDate(LocalDate date)
    {
        return date.withDayOfMonth(1);
    }
}
