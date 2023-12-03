package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.settlement.domain.Settlement;
import com.ciosmak.automotivepartner.settlement.repository.SettlementRepository;
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
public class SettlementRepositoryTest
{
    private static int numberOfSettlements;

    @Autowired
    private SettlementRepository settlementRepository;

    private User user;

    @BeforeEach
    public void setUp()
    {
        user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        List<Settlement> settlements = new ArrayList<>();
        settlements.add(Settlement.builder().date(LocalDate.of(2023, 10, 1)).netProfit(BigDecimal.valueOf(1000)).factor(BigDecimal.ONE).tips(BigDecimal.valueOf(100)).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(1100)).isBugReported(Boolean.FALSE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2023, 11, 1)).netProfit(BigDecimal.valueOf(2000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(2000)).isBugReported(Boolean.TRUE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2023, 12, 1)).netProfit(BigDecimal.valueOf(3000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(3000)).isBugReported(Boolean.TRUE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2024, 1, 1)).netProfit(BigDecimal.valueOf(4000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(4000)).isBugReported(Boolean.FALSE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2024, 2, 1)).netProfit(BigDecimal.valueOf(5000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(5000)).isBugReported(Boolean.FALSE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2024, 3, 1)).netProfit(BigDecimal.valueOf(6000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(6000)).isBugReported(Boolean.FALSE).user(user).build());
        settlements.add(Settlement.builder().date(LocalDate.of(2024, 4, 1)).netProfit(BigDecimal.valueOf(7000)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(7000)).isBugReported(Boolean.FALSE).user(user).build());
        settlementRepository.saveAll(settlements);
        numberOfSettlements = settlements.size();
    }

    @Test
    public void shouldReturnSettlementWhenSettlementIsInDatabase()
    {
        Settlement savedSettlement = settlementRepository.save(Settlement.builder().date(LocalDate.of(2022, 10, 1)).netProfit(BigDecimal.valueOf(999)).factor(BigDecimal.ONE).tips(BigDecimal.ZERO).penalties(BigDecimal.ZERO).finalProfit(BigDecimal.valueOf(999)).isBugReported(Boolean.FALSE).user(user).build());

        Assertions.assertThat(savedSettlement).isNotNull();
        Assertions.assertThat(savedSettlement.getDate()).isEqualTo(LocalDate.of(2022, 10, 1));
        Assertions.assertThat(savedSettlement.getNetProfit()).isCloseTo(BigDecimal.valueOf(999), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedSettlement.getFactor()).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(0.01));
        Assertions.assertThat(savedSettlement.getTips()).isCloseTo(BigDecimal.ZERO, Percentage.withPercentage(0.01));
        Assertions.assertThat(savedSettlement.getPenalties()).isCloseTo(BigDecimal.ZERO, Percentage.withPercentage(0.01));
        Assertions.assertThat(savedSettlement.getFinalProfit()).isCloseTo(BigDecimal.valueOf(999), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedSettlement.getIsBugReported()).isEqualTo(Boolean.FALSE);
        Assertions.assertThat(savedSettlement.getUser()).isEqualTo(user);
    }

    @Test
    public void shouldFindByIdWhenIdIsCorrect()
    {
        long firstId = settlementRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfSettlements;
        for (long i = firstId; i < lastId; ++i)
        {
            Optional<Settlement> foundSettlement = settlementRepository.findById(i);

            Assertions.assertThat(foundSettlement).isNotEmpty();
        }
    }

    @Test
    public void shouldNotFindByIdWhenIdIsIncorrect()
    {
        for (long i = 999L; i < numberOfSettlements; ++i)
        {

            Optional<Settlement> foundSettlement = settlementRepository.findById(i);
            Assertions.assertThat(foundSettlement).isEmpty();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10",
            "2023, 11",
            "2023, 12",
            "2024, 1",
            "2024, 2",
            "2024, 3",
            "2024, 4"
    })
    public void shouldFindByUserAndDateWhenSettlementIsInDatabase(int year, int month)
    {
        LocalDate date = LocalDate.of(year, month, 1);

        Optional<Settlement> foundSettlement = settlementRepository.findByUserIdAndDate(user.getId(), date);

        Assertions.assertThat(foundSettlement).isNotEmpty();
    }

    @Test
    public void shouldNotFindByUserAndDateWhenSettlementIsNotInDatabase()
    {
        Optional<Settlement> foundSettlement = settlementRepository.findByUserIdAndDate(user.getId(), LocalDate.of(2022, 10, 1));

        Assertions.assertThat(foundSettlement).isEmpty();
    }

    @Test
    public void shouldFindAllWithBugReportedTrueWhenSettlementsAreInDatabase()
    {
        List<Settlement> foundSettlements = settlementRepository.findAllWithBugReportedTrue();

        Assertions.assertThat(foundSettlements).isNotEmpty();
        Assertions.assertThat(foundSettlements.size()).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 2023, 10",
            "2000, 2023, 11",
            "3000, 2023, 12",
            "4000, 2024, 1",
            "5000, 2024, 2",
            "6000, 2024, 3",
            "7000, 2024, 4"
    })
    public void shouldFindNetProfitByYearMonthAndUserIdWhenExists(Integer netProfit, Integer year, Integer month)
    {
        Optional<BigDecimal> foundNetProfit = settlementRepository.findNetProfitByYearMonthAndUserId(year, month, user.getId());

        Assertions.assertThat(foundNetProfit).isNotEmpty();
        Assertions.assertThat(foundNetProfit.get()).isCloseTo(BigDecimal.valueOf(netProfit), Percentage.withPercentage(0.01));
    }

    @Test
    public void shouldNotFindNetProfitByYearMonthAndUserIdWhenDoesNotExists()
    {
        Optional<BigDecimal> foundNetProfit = settlementRepository.findNetProfitByYearMonthAndUserId(2022, 1, user.getId());

        Assertions.assertThat(foundNetProfit).isEmpty();
    }
}
