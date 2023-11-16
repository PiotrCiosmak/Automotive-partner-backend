package com.ciosmak.automotivepartner.statistic.api;

import com.ciosmak.automotivepartner.statistic.api.response.OverallStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.StatisticsResponse;
import com.ciosmak.automotivepartner.statistic.api.response.YearStatisticsResponse;
import com.ciosmak.automotivepartner.statistic.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;

@Tag(name = "statistics")
@AllArgsConstructor
@RestController
@RequestMapping("api/statistics")
public class StatisticsApi
{
    private final StatisticsService statisticsService;

    @GetMapping("/find/overall/{user_id}")
    @Operation(summary = "Find overall")
    public ResponseEntity<OverallStatisticsResponse> findOverall(@PathVariable Long user_id)
    {
        OverallStatisticsResponse overallStatisticsResponse = statisticsService.findOverall(user_id);
        return ResponseEntity.status(HttpStatus.FOUND).body(overallStatisticsResponse);
    }

    @GetMapping("/find/detail/{user_id}")
    @Operation(summary = "Find in selected month")
    public ResponseEntity<StatisticsResponse> findInSelectedMonth(@PathVariable Long user_id, @RequestParam LocalDate date)
    {
        StatisticsResponse statisticsResponse = statisticsService.findInSelectedMonth(user_id, date);
        return ResponseEntity.status(HttpStatus.FOUND).body(statisticsResponse);
    }

    @GetMapping("/find/{year}")
    @Operation(summary = "Find in selected year")
    public ResponseEntity<YearStatisticsResponse> findInSelectedYear(@RequestParam Year year)
    {
        YearStatisticsResponse yearStatisticsResponse = statisticsService.findInSelectedYear(year);
        return ResponseEntity.status(HttpStatus.FOUND).body(yearStatisticsResponse);
    }
}
