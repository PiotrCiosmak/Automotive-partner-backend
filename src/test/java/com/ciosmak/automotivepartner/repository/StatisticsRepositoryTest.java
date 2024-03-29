package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StatisticsRepositoryTest
{
    @Autowired
    private StatisticsRepository statisticsRepository;

    private User user;

    @BeforeEach
    public void setUp()
    {
        user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        List<Statistics> statistics = new ArrayList<>();
        statistics.add(Statistics.builder().date(LocalDate.of(2023, 10, 1)).mileage(1000).lpg(BigDecimal.valueOf(100)).petrol(BigDecimal.valueOf(10)).user(user).build());
        statistics.add(Statistics.builder().date(LocalDate.of(2023, 11, 1)).mileage(2000).lpg(BigDecimal.valueOf(200)).petrol(BigDecimal.valueOf(20)).user(user).build());
        statistics.add(Statistics.builder().date(LocalDate.of(2023, 12, 1)).mileage(3000).lpg(BigDecimal.valueOf(300)).petrol(BigDecimal.valueOf(30)).user(user).build());
        statistics.add(Statistics.builder().date(LocalDate.of(2024, 1, 1)).mileage(4000).lpg(BigDecimal.valueOf(400)).petrol(BigDecimal.valueOf(40)).user(user).build());
        statistics.add(Statistics.builder().date(LocalDate.of(2024, 2, 1)).mileage(5000).lpg(BigDecimal.valueOf(500)).petrol(BigDecimal.valueOf(50)).user(user).build());
        statisticsRepository.saveAll(statistics);
    }

    @Test
    public void shouldSaveStatistics()
    {
        Statistics savedStatistics = statisticsRepository.save(Statistics.builder().date(LocalDate.of(2022, 10, 1)).mileage(999).lpg(BigDecimal.valueOf(99)).petrol(BigDecimal.valueOf(9)).user(user).build());

        Assertions.assertThat(savedStatistics).isNotNull();
        Assertions.assertThat(savedStatistics.getDate()).isEqualTo(LocalDate.of(2022, 10, 1));
        Assertions.assertThat(savedStatistics.getMileage()).isEqualTo(999);
        Assertions.assertThat(savedStatistics.getLpg()).isCloseTo(BigDecimal.valueOf(99), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedStatistics.getPetrol()).isCloseTo(BigDecimal.valueOf(9), Percentage.withPercentage(0.01));
    }

    @Test
    public void shouldFindStatisticsByUserIdWhenStatisticsConnectedToThatUserIsInDatabase()
    {
        List<Statistics> foundStatistics = statisticsRepository.findByUserId(user.getId());

        Assertions.assertThat(foundStatistics).isNotEmpty();
        Assertions.assertThat(foundStatistics.size()).isEqualTo(5);
    }

    @Test
    public void shouldNotFindStatisticsByUserIdWhenStatisticsConnectedToThatUserIsNotInDatabase()
    {
        List<Statistics> foundStatistics = statisticsRepository.findByUserId(99L);

        Assertions.assertThat(foundStatistics).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10",
            "2023, 11",
            "2023, 12",
            "2024, 1",
            "2024, 2"
    })
    public void shouldFindStatisticsByUserIdAndDateWhenStatisticsFormThisDateConnectedToThatUserIsInDatabase(int year, int month)
    {
        LocalDate date = LocalDate.of(year, month, 1);

        Optional<Statistics> foundStatistics = statisticsRepository.findByUserIdAndDate(user.getId(), date);

        Assertions.assertThat(foundStatistics).isNotEmpty();
    }

    @Test
    public void shouldNotFindStatisticsByUserIdAndDateWhenStatisticsFormThisDateConnectedToThatUserIsNotInDatabase()
    {
        Optional<Statistics> foundStatistics = statisticsRepository.findByUserIdAndDate(user.getId(), LocalDate.of(2022, 10, 1));

        Assertions.assertThat(foundStatistics).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 6000",
            "2024, 9000"
    })
    public void shouldSumMileageByYear(int year, int expectedMileage)
    {
        Integer mileage = statisticsRepository.sumMileageByYear(year);

        Assertions.assertThat(mileage).isEqualTo(expectedMileage);
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 600",
            "2024, 900"
    })
    public void shouldSumLpgByYear(int year, int expectedLpg)
    {
        BigDecimal lpg = statisticsRepository.sumLpgByYear(year);

        Assertions.assertThat(lpg).isCloseTo(BigDecimal.valueOf(expectedLpg), Percentage.withPercentage(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 60",
            "2024, 90"
    })
    public void shouldSumPetrolByYear(int year, int expectedPetrol)
    {
        BigDecimal petrol = statisticsRepository.sumPetrolByYear(year);

        Assertions.assertThat(petrol).isCloseTo(BigDecimal.valueOf(expectedPetrol), Percentage.withPercentage(0.01));
    }
}
