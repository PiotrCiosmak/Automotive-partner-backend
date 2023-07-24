package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.api.request.StatisticsRequest;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StatisticsMapper
{
    private final UserRepository userRepository;

    public Statistics toStatistics(StatisticsRequest statisticsRequest)
    {
        return new Statistics(statisticsRequest.getMonthAndYear(), statisticsRequest.getMileage(), statisticsRequest.getLpg(), statisticsRequest.getPetrol(), userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
    }

    public Statistics toStatistics(Statistics statistics, StatisticsRequest statisticsRequest)
    {
        statistics.setMonthAndYear(statisticsRequest.getMonthAndYear());
        statistics.setMileage(statisticsRequest.getMileage());
        statistics.setLpg(statisticsRequest.getLpg());
        statistics.setPetrol(statisticsRequest.getPetrol());
        statistics.setUser(userRepository.findById(statisticsRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(statisticsRequest.getUserId())));
        return statistics;
    }

    public StatisticsResponse toStatisticsResponse(Statistics statistics)
    {
        return new StatisticsResponse(statistics.getId(), statistics.getMonthAndYear(), statistics.getMileage(), statistics.getLpg(), statistics.getPetrol(), statistics.getUser().getId());
    }

}
