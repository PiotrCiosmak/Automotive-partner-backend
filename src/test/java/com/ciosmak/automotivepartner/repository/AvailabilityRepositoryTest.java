package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.availability.repository.AvailabilityRepository;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AvailabilityRepositoryTest
{
    private static int numberOfAvailability;

    @Autowired
    private AvailabilityRepository availabilityRepository;
    private User user;

    @BeforeEach
    public void setUp()
    {
        user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(Availability.builder().type(Type.DAY).date(LocalDate.of(2023, 10, 1)).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.NIGHT).date(LocalDate.of(2023, 11, 1)).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.FREE).date(LocalDate.of(2023, 12, 1)).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.FREE).date(LocalDate.of(2024, 1, 1)).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.DAY).date(LocalDate.of(2024, 2, 1)).isUsed(Boolean.FALSE).user(user).build());
        availabilities.add(Availability.builder().type(Type.NIGHT).date(LocalDate.of(2024, 3, 1)).isUsed(Boolean.FALSE).user(user).build());
        availabilities.add(Availability.builder().type(Type.FREE).date(LocalDate.of(2024, 4, 1)).isUsed(Boolean.FALSE).user(user).build());
        availabilityRepository.saveAll(availabilities);
        numberOfAvailability = availabilities.size();
    }

    @Test
    public void shouldSaveAvailability()
    {
        Availability savedAvailability = availabilityRepository.save(Availability.builder().type(Type.FREE).date(LocalDate.of(2024, 5, 1)).isUsed(Boolean.FALSE).user(user).build());

        Assertions.assertThat(savedAvailability).isNotNull();
        Assertions.assertThat(savedAvailability.getType()).isEqualTo(Type.FREE);
        Assertions.assertThat(savedAvailability.getDate()).isEqualTo(LocalDate.of(2024, 5, 1));
        Assertions.assertThat(savedAvailability.getIsUsed()).isEqualTo(Boolean.FALSE);
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10, 1",
            "2023, 11, 1",
            "2023, 12, 1",
            "2024, 1, 1",
            "2024, 2, 1",
            "2024, 3, 1",
            "2024, 4, 1"
    })
    public void shouldFindAvailabilityByUserIdAndDateWhenAvailabilityIsForThatDayAndIsConnectedWithThisUserIsInDatabase(int year, int month, int day)
    {
        Optional<Availability> foundAvailability = availabilityRepository.findByUser_IdAndDate(user.getId(), LocalDate.of(year, month, day));

        Assertions.assertThat(foundAvailability).isNotEmpty();
    }

    @Test
    public void shouldNotFindAvailabilityByUserIdAndDateWhenAvailabilityIsForThatDayAndIsConnectedWithThisUserIsNotInDatabase()
    {
        Optional<Availability> foundAvailability = availabilityRepository.findByUser_IdAndDate(user.getId(), LocalDate.of(2022, 10, 1));

        Assertions.assertThat(foundAvailability).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10, 1, DAY, 1",
            "2023, 10, 1, NIGHT, 0",
            "2023, 10, 1, FREE, 0",
            "2023, 11, 1, DAY, 0",
            "2023, 11, 1, NIGHT, 1",
            "2023, 11, 1, FREE, 0",
            "2023, 12, 1, DAY, 0",
            "2023, 12, 1, NIGHT, 0",
            "2023, 12, 1, FREE, 1",
            "2024, 1, 1, DAY, 0",
            "2024, 1, 1, NIGHT, 0",
            "2024, 1, 1, FREE, 1",
            "2024, 2, 1, DAY, 1",
            "2024, 2, 1, NIGHT, 0",
            "2024, 2, 1, FREE, 0",
            "2024, 3, 1, DAY, 0",
            "2024, 3, 1, NIGHT, 1",
            "2024, 3, 1, FREE, 0",
            "2024, 4, 1, DAY, 0",
            "2024, 4, 1, NIGHT, 0",
            "2024, 4, 1, FREE, 1"
    })
    public void shouldCountAvailabilitiesByDateAndType(int year, int month, int day, Type type, int expectedNumberOfAvailabilities)
    {
        Integer numberOfAvailability = availabilityRepository.countByDateAndType(LocalDate.of(year, month, day), type);

        Assertions.assertThat(numberOfAvailability).isEqualTo(expectedNumberOfAvailabilities);
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10, 1, DAY",
            "2023, 11, 1, NIGHT",
            "2023, 12, 1, FREE",
            "2024, 1, 1, FREE",
            "2024, 2, 1, DAY",
            "2024, 3, 1, NIGHT",
            "2024, 4, 1, FREE"
    })
    public void shouldFindAllAvailabilitiesByDateAndTypeWhenAvailabilitiesForThatDateAndWithThisTypeAreInDatabase(int year, int month, int day, Type type)
    {
        List<Availability> foundAvailabilities = availabilityRepository.findAllByDateAndType(LocalDate.of(year, month, day), type);

        Assertions.assertThat(foundAvailabilities).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10, 1, NIGHT, 0",
            "2023, 10, 1, FREE, 0",
            "2023, 11, 1, DAY, 0",
            "2023, 11, 1, FREE, 0",
            "2023, 12, 1, DAY, 0",
            "2023, 12, 1, NIGHT, 0",
            "2024, 1, 1, DAY, 0",
            "2024, 1, 1, NIGHT, 0",
            "2024, 2, 1, NIGHT, 0",
            "2024, 2, 1, FREE, 0",
            "2024, 3, 1, DAY, 0",
            "2024, 3, 1, FREE, 0",
            "2024, 4, 1, DAY, 0",
            "2024, 4, 1, NIGHT, 0",
    })
    public void shouldFindZeroAvailabilitiesByDateAndTypeWhenAvailabilitiesForThatDateAndWithThisTypeAreNotInDatabase(int year, int month, int day, Type type)
    {
        List<Availability> foundAvailabilities = availabilityRepository.findAllByDateAndType(LocalDate.of(year, month, day), type);

        Assertions.assertThat(foundAvailabilities).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2024, 2, 1, DAY",
            "2024, 3, 1, NIGHT",
            "2024, 4, 1, FREE"
    })
    public void shouldFindAllAvailabilitiesByDateAndTypeAndIsUsedFalseWhenAvailabilitiesForThatDateAndWithThisTypeAndUnusedAreInDatabase(int year, int month, int day, Type type)
    {
        List<Availability> foundAvailabilities = availabilityRepository.findAllByDateAndTypeAndIsUsedFalse(LocalDate.of(year, month, day), type);

        Assertions.assertThat(foundAvailabilities).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 10, 1, DAY",
            "2023, 11, 1, NIGHT",
            "2023, 12, 1, FREE",
            "2024, 1, 1, FREE",
            "2023, 10, 1, NIGHT",
            "2023, 10, 1, FREE",
            "2023, 11, 1, DAY",
            "2023, 11, 1, FREE",
            "2023, 12, 1, DAY",
            "2023, 12, 1, NIGHT",
            "2024, 1, 1, DAY",
            "2024, 1, 1, NIGHT",
            "2024, 2, 1, NIGHT",
            "2024, 2, 1, FREE",
            "2024, 3, 1, DAY",
            "2024, 3, 1, FREE",
            "2024, 4, 1, DAY",
            "2024, 4, 1, NIGHT"
    })
    public void shouldFindZeroAvailabilitiesByDateAndTypeAndIsUsedFalseWhenAvailabilitiesForThatDateAndWithThisTypeAndUnusedAreNotInDatabase(int year, int month, int day, Type type)
    {
        List<Availability> foundAvailabilities = availabilityRepository.findAllByDateAndTypeAndIsUsedFalse(LocalDate.of(year, month, day), type);

        Assertions.assertThat(foundAvailabilities).isEmpty();
    }

    @Test
    public void shouldFindAllAvailabilitiesByIsUsedFalseAndDateFutureWhenFutureUnusedAvailabilitiesAreInDatabase()
    {
        List<Availability> foundAvailabilities = availabilityRepository.findAllByIsUsedFalseAndDateFuture();

        Assertions.assertThat(foundAvailabilities).isNotEmpty();
        Assertions.assertThat(foundAvailabilities.size()).isEqualTo(3);
    }

    @Test
    public void shouldFindZeroAvailabilitiesByIsUsedFalseAndDateFutureWhenFutureUnusedAvailabilitiesAreNotInDatabase()
    {
        availabilityRepository.deleteAll();
        loadAvailabilityWithPastOrPresentDateOrUsed();

        List<Availability> foundAvailabilities = availabilityRepository.findAllByIsUsedFalseAndDateFuture();

        Assertions.assertThat(foundAvailabilities).isEmpty();
    }

    void loadAvailabilityWithPastOrPresentDateOrUsed()
    {
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(Availability.builder().type(Type.DAY).date(LocalDate.MAX).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.NIGHT).date(LocalDate.MAX).isUsed(Boolean.TRUE).user(user).build());
        availabilities.add(Availability.builder().type(Type.FREE).date(LocalDate.now().minusYears(1)).isUsed(Boolean.FALSE).user(user).build());
        availabilities.add(Availability.builder().type(Type.FREE).date(LocalDate.MAX).isUsed(Boolean.TRUE).user(user).build());
        availabilityRepository.saveAll(availabilities);
    }

    @Test
    public void shouldDeleteAvailabilityByIdWhenAvailabilityWithThisIsInDatabase()
    {
        long firstId = availabilityRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfAvailability;
        for (long i = firstId; i < lastId; ++i)
        {
            availabilityRepository.deleteById(i);
            Optional<Availability> foundCar = availabilityRepository.findById(i);

            Assertions.assertThat(foundCar).isEmpty();
        }
    }
}
