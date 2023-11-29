package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.statistic.domain.Statistics;
import com.ciosmak.automotivepartner.statistic.repository.StatisticsRepository;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void shouldReturnStatisticsWhenStatisticsAreInDatabase()
    {
        Statistics savedStatistics = statisticsRepository.save(Statistics.builder().date(LocalDate.of(2022, 10, 1)).mileage(999).lpg(BigDecimal.valueOf(99)).petrol(BigDecimal.valueOf(9)).user(user).build());
        Assertions.assertThat(savedStatistics).isNotNull();
        Assertions.assertThat(savedStatistics.getDate()).isEqualTo(LocalDate.of(2022, 10, 1));
        Assertions.assertThat(savedStatistics.getMileage()).isEqualTo(999);
        Assertions.assertThat(savedStatistics.getLpg()).isCloseTo(BigDecimal.valueOf(99), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedStatistics.getPetrol()).isCloseTo(BigDecimal.valueOf(9), Percentage.withPercentage(0.01));
    }

    @Test
    public void shouldFindStatisticByUserIdWhenStatisticIsInDatabase()
    {
        List<Statistics> foundStatistics = statisticsRepository.findByUserId(user.getId());
        Assertions.assertThat(foundStatistics).isNotEmpty();
        Assertions.assertThat(foundStatistics.size()).isEqualTo(5);
    }

    @Test
    public void shouldNotFindStatisticByUserIdWhenStatisticIsNotInDatabase()
    {
        List<Statistics> foundStatistics = statisticsRepository.findByUserId(99L);
        Assertions.assertThat(foundStatistics).isEmpty();
    }

    @Test
    public void shouldFindStatisticByUserIdAndDateWhenStatisticIsInDatabase()
    {
        LocalDate date = LocalDate.of(2023, 10, 1);
        while (date.isBefore(LocalDate.of(2024, 3, 1)))
        {
            Optional<Statistics> foundStatistics = statisticsRepository.findByUserIdAndDate(user.getId(), date);
            Assertions.assertThat(foundStatistics).isNotEmpty();
            date = date.plusMonths(1);
        }
    }

    @Test
    public void shouldNotFindStatisticByUserIdAndDateWhenStatisticIsNotInDatabase()
    {
        Optional<Statistics> foundStatistics = statisticsRepository.findByUserIdAndDate(user.getId(), LocalDate.of(2022, 10, 1));
        Assertions.assertThat(foundStatistics).isEmpty();
    }

    @Test
    public void shouldSumMileageByYear()
    {
        Integer mileage = statisticsRepository.sumMileageByYear(2023);
        Assertions.assertThat(mileage).isEqualTo(6000);

        mileage = statisticsRepository.sumMileageByYear(2024);
        Assertions.assertThat(mileage).isEqualTo(9000);

        mileage = statisticsRepository.sumMileageByYear(2025);
        Assertions.assertThat(mileage).isNull();
    }

    @Test
    public void shouldSumLpgByYear()
    {
        BigDecimal lpg = statisticsRepository.sumLpgByYear(2023);
        Assertions.assertThat(lpg).isCloseTo(BigDecimal.valueOf(600), Percentage.withPercentage(0.01));

        lpg = statisticsRepository.sumLpgByYear(2024);
        Assertions.assertThat(lpg).isCloseTo(BigDecimal.valueOf(900), Percentage.withPercentage(0.01));

        lpg = statisticsRepository.sumLpgByYear(2025);
        Assertions.assertThat(lpg).isNull();
    }

    @Test
    public void shouldSumPetrolByYear()
    {
        BigDecimal petrol = statisticsRepository.sumPetrolByYear(2023);
        Assertions.assertThat(petrol).isCloseTo(BigDecimal.valueOf(60), Percentage.withPercentage(0.01));

        petrol = statisticsRepository.sumPetrolByYear(2024);
        Assertions.assertThat(petrol).isCloseTo(BigDecimal.valueOf(90), Percentage.withPercentage(0.01));

        petrol = statisticsRepository.sumPetrolByYear(2025);
        Assertions.assertThat(petrol).isNull();
    }
}
