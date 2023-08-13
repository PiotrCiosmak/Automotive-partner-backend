package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.api.request.StatisticsRequest;
import com.ciosmak.automotivepartner.statistic.api.response.OverallStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.YearStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@Component
public class StatisticsMapper
{
    private final UserRepository userRepository;

    public Statistics toStatistics(StatisticsRequest statisticsRequest)
    {
        return new Statistics(statisticsRequest.getDate(), statisticsRequest.getMileage(), statisticsRequest.getLpg(), statisticsRequest.getPetrol(), userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
    }

    public Statistics toStatistics(Statistics statistics, StatisticsRequest statisticsRequest)
    {
        statistics.setDate(statisticsRequest.getDate());
        statistics.setMileage(statisticsRequest.getMileage());
        statistics.setLpg(statisticsRequest.getLpg());
        statistics.setPetrol(statisticsRequest.getPetrol());
        statistics.setUser(userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
        return statistics;
    }

    public StatisticsResponse toStatisticsResponse(Statistics statistics)
    {
        return new StatisticsResponse(statistics.getId(), statistics.getDate(), statistics.getMileage(), statistics.getLpg(), statistics.getPetrol(), statistics.getUser().getId());
    }

    public OverallStatisticsResponse toOverallStatisticsResponse(Statistics statistics)
    {
        return new OverallStatisticsResponse(statistics.getMileage(), statistics.getLpg(), statistics.getPetrol(), statistics.getUser().getId());
    }

    public OverallStatisticsResponse toEmptyOverallStatisticsResponse(Long userId)
    {
        return new OverallStatisticsResponse(0, BigDecimal.ZERO, BigDecimal.ZERO, userId);
    }

    public YearStatisticsResponse toYearStatisticsResponse(Map<String, BigDecimal> yearStatistics)
    {
        return new YearStatisticsResponse(yearStatistics.get("mileage").intValue(), yearStatistics.get("lpg"), yearStatistics.get("petrol"));
    }
}
