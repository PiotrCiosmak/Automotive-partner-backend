package com.ciosmak.automotivepartner.statistic.api;

import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

@Tag(name = "statistics")
@AllArgsConstructor
@RestController
@RequestMapping("api/statistics")
public class StatisticsApi
{
    private final StatisticsService statisticsService;

    @GetMapping("/find/{user_id}")
    @Operation(summary = "Get user overall statistics")
    public ResponseEntity<StatisticsResponse> findUserOverall(@PathVariable Long user_id)
    {
        StatisticsResponse statisticsResponse = statisticsService.findUserOverall(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(statisticsResponse);
    }

    @GetMapping("/find/{user_id}/{date}")
    @Operation(summary = "Get user statistics in selected month")
    public ResponseEntity<StatisticsResponse> findUserInSelectedMonth(@PathVariable Long user_id, YearMonth date)
    {
        StatisticsResponse statisticsResponse = statisticsService.findUserInSelectedMonth(user_id, date);
        return ResponseEntity.status(HttpStatus.OK).body(statisticsResponse);
    }

    @GetMapping("/find/{year}")
    @Operation(summary = "Get whole statistic in selected year")
    public ResponseEntity<StatisticsResponse> findInSelectedYear(@PathVariable Year year)
    {
        StatisticsResponse statisticsResponse = statisticsService.findInSelectedYear(year);
        return ResponseEntity.status(HttpStatus.OK).body(statisticsResponse);
    }
}
