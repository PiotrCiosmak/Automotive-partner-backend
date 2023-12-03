package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.accident.domain.Accident;
import com.ciosmak.automotivepartner.accident.repository.AccidentRepository;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
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
public class AccidentRepositoryTest
{
    private static int numberOfAccidents;

    private final List<Shift> shifts = new ArrayList<>();
    @Autowired
    private AccidentRepository accidentRepository;
    private User user;

    @BeforeEach
    public void setUp()
    {
        Car car = Car.builder().registrationNumber("AA12345").mileage(1000).isBlocked(Boolean.FALSE).build();
        user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.DAY).startMileage(1000).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(1)).endMileage(1200).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(car).user(user).build());
        shifts.add(Shift.builder().date(LocalDate.of(2024, 1, 1)).type(Type.DAY).startMileage(1200).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(1)).endMileage(1500).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(car).user(user).build());
        shifts.add(Shift.builder().date(LocalDate.of(2024, 2, 1)).type(Type.DAY).startMileage(5000).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(1)).endMileage(5500).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(car).user(user).build());

        List<Accident> accidents = new ArrayList<>();
        accidents.add(Accident.builder().isGuilty(Boolean.TRUE).isEndOfWork(Boolean.FALSE).shift(shifts.get(0)).build());
        accidents.add(Accident.builder().isGuilty(Boolean.FALSE).isEndOfWork(Boolean.FALSE).shift(shifts.get(1)).build());
        accidents.add(Accident.builder().isGuilty(Boolean.FALSE).isEndOfWork(Boolean.FALSE).shift(shifts.get(2)).build());
        accidentRepository.saveAll(accidents);
        numberOfAccidents = accidents.size();
    }

    @Test
    public void shouldSaveAccident()
    {
        Accident savedAccident = accidentRepository.save(Accident.builder().isGuilty(Boolean.FALSE).isEndOfWork(Boolean.TRUE).shift(shifts.get(0)).build());

        Assertions.assertThat(savedAccident).isNotNull();
        Assertions.assertThat(savedAccident.getIsGuilty()).isEqualTo(Boolean.FALSE);
        Assertions.assertThat(savedAccident.getIsEndOfWork()).isEqualTo(Boolean.TRUE);
        Assertions.assertThat(savedAccident.getShift()).isEqualTo(shifts.get(0));
    }

    @Test
    public void shouldFindAccidentByShiftIdWhenAccidentConnectedWithThisShiftIsInDatabase()
    {
        Optional<Accident> foundAccident = accidentRepository.findByShiftId(shifts.get(0).getId());

        Assertions.assertThat(foundAccident).isNotEmpty();
    }

    @Test
    public void shouldNotFindAccidentByShiftIdWhenAccidentConnectedWithThisShiftIsNotInDatabase()
    {
        Optional<Accident> foundAccident = accidentRepository.findByShiftId(999L);

        Assertions.assertThat(foundAccident).isEmpty();
    }

    @Test
    public void shouldCountGuiltyAccidentsByUserId()
    {
        Integer numberOfGuiltyAccidents = accidentRepository.countGuiltyAccidentsByUserId(user.getId());

        Assertions.assertThat(numberOfGuiltyAccidents).isEqualTo(1);
    }

    @Test
    public void shouldFindAllAccidents()
    {
        List<Accident> foundAccidents = accidentRepository.findAll();

        Assertions.assertThat(foundAccidents).isNotEmpty();
        Assertions.assertThat(foundAccidents.size()).isEqualTo(3);
    }

    @Test
    public void shouldFindZeroAccidentsWhenAccidentsAreNotInDatabase()
    {
        accidentRepository.deleteAll();

        List<Accident> foundAccidents = accidentRepository.findAll();

        Assertions.assertThat(foundAccidents).isEmpty();
    }

    @Test
    public void shouldFindAccidentByIdWhenAccidentWithThisIdIsInDatabase()
    {
        long firstId = accidentRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfAccidents;
        for (long i = firstId; i < lastId; ++i)
        {
            Optional<Accident> foundSettlement = accidentRepository.findById(i);

            Assertions.assertThat(foundSettlement).isNotEmpty();
        }
    }

    @Test
    public void shouldNotFindAccidentByIdWhenAccidentWithThisIdIsNotInDatabase()
    {
        for (long i = 999L; i < numberOfAccidents; ++i)
        {
            Optional<Accident> foundSettlement = accidentRepository.findById(i);

            Assertions.assertThat(foundSettlement).isEmpty();
        }
    }
}
