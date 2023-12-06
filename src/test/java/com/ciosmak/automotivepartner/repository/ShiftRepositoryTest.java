package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.support.Type;
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
public class ShiftRepositoryTest
{
    private static int numberOfShifts;
    private final List<User> users = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();

    @Autowired
    private ShiftRepository shiftRepository;

    @BeforeEach
    public void setUp()
    {
        users.add(User.builder().firstName("TestA").lastName("TestQ").email("test.a@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build());
        users.add(User.builder().firstName("TestB").lastName("TestW").email("test.b@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).isBlocked(Boolean.TRUE).build());
        users.add(User.builder().firstName("TestC").lastName("TestE").email("test.c@example.com").password("Test123_").phoneNumber("123456789").role(Role.ADMIN).build());

        cars.add(Car.builder().registrationNumber("AA12345").mileage(1000).isBlocked(Boolean.FALSE).build());
        cars.add(Car.builder().registrationNumber("BB12345").mileage(2000).isBlocked(Boolean.FALSE).build());
        cars.add(Car.builder().registrationNumber("CC12345").mileage(3000).isBlocked(Boolean.FALSE).build());

        List<Shift> shifts = new ArrayList<>();
        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.DAY).startMileage(1000).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(1)).endMileage(1100).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.DAY).startMileage(2000).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1200).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());
        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.DAY).startMileage(3000).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3300).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(2)).user(users.get(2)).build());

        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.NIGHT).startMileage(1100).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1200).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 1)).type(Type.NIGHT).startMileage(1200).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1400).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 2)).type(Type.DAY).startMileage(1200).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1300).isStarted(Boolean.TRUE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 2)).type(Type.DAY).startMileage(1400).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.of(2023, 12, 3)).type(Type.DAY).startMileage(3300).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(2)).user(users.get(2)).build());

        shiftRepository.saveAll(shifts);
        numberOfShifts = shifts.size();
    }

    @Test
    public void shouldSaveShift()
    {
        Shift savedShift = shiftRepository.save(Shift.builder().date(LocalDate.of(2022, 1, 1)).type(Type.DAY).startMileage(1000).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(1)).endMileage(1100).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());

        Assertions.assertThat(savedShift).isNotNull();
        Assertions.assertThat(savedShift.getDate()).isEqualTo(LocalDate.of(2022, 1, 1));
        Assertions.assertThat(savedShift.getType()).isEqualTo(Type.DAY);
        Assertions.assertThat(savedShift.getStartMileage()).isEqualTo(1000);
        Assertions.assertThat(savedShift.getLpg()).isCloseTo(BigDecimal.valueOf(10), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedShift.getPetrol()).isCloseTo(BigDecimal.valueOf(1), Percentage.withPercentage(0.01));
        Assertions.assertThat(savedShift.getEndMileage()).isEqualTo(1100);
        Assertions.assertThat(savedShift.getIsStarted()).isEqualTo(Boolean.TRUE);
        Assertions.assertThat(savedShift.getIsDone()).isEqualTo(Boolean.TRUE);
        Assertions.assertThat(savedShift.getIsCarAvailable()).isEqualTo(Boolean.TRUE);
        Assertions.assertThat(savedShift.getCar()).isEqualTo(cars.get(0));
        Assertions.assertThat(savedShift.getUser()).isEqualTo(users.get(0));
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 12, 1, 5",
            "2023, 12, 2, 2",
            "2023, 12, 3, 1"
    })
    public void shouldFindAllShiftsByDateWhenShiftsForThatDateAreInDataset(int year, int month, int day, int expectedNumberOfShifts)
    {
        List<Shift> foundShifts = shiftRepository.findAllByDate(LocalDate.of(year, month, day));

        Assertions.assertThat(foundShifts).isNotEmpty();
        Assertions.assertThat(foundShifts.size()).isEqualTo(expectedNumberOfShifts);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2023, 12, 1, true, true",
            "1, 2023, 12, 1, true, true",
            "2, 2023, 12, 1, true, false",
            "0, 2023, 12, 2, true, false",
            "1, 2023, 12, 2, true, false",
            "2, 2023, 12, 2, false, false",
            "0, 2023, 12, 3, false, false",
            "1, 2023, 12, 3, false, false",
            "2, 2023, 12, 3, true, false",
            "0, 2022, 1, 1, false, false",
            "1, 2022, 1, 1, false, false",
            "2, 2022, 1, 1, false, false"
    })
    public void shouldFindShiftByUserIdAndDateAndTypeWhenShiftForThatDateAndWithThisTypeIsConnectedToThisUserIdIsInDatabase(int userIndex, int year, int month, int day, boolean expectedDayShift, boolean expectedNightShift)
    {
        Optional<Shift> foundDayShift = shiftRepository.findByUser_IdAndDateAndType(users.get(userIndex).getId(), LocalDate.of(year, month, day), Type.DAY);
        if (expectedDayShift)
        {
            Assertions.assertThat(foundDayShift).isNotEmpty();
        }
        else
        {
            Assertions.assertThat(foundDayShift).isEmpty();
        }


        Optional<Shift> foundNightShift = shiftRepository.findByUser_IdAndDateAndType(users.get(userIndex).getId(), LocalDate.of(year, month, day), Type.NIGHT);
        if (expectedNightShift)
        {
            Assertions.assertThat(foundNightShift).isNotEmpty();
        }
        else
        {
            Assertions.assertThat(foundNightShift).isEmpty();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 12, 1, 3, 2",
            "2023, 12, 2, 2, 0",
            "2023, 12, 3, 1, 0"
    })
    public void shouldFindAllShiftsByDateAndTypeWhenShiftsForThatDateAndWithThisTypeAreInDataset(int year, int month, int day, int expectedNumberOfDayShifts, int expectedNumberOfNightShifts)
    {
        List<Shift> foundDayShifts = shiftRepository.findAllByDateAndType(LocalDate.of(year, month, day), Type.DAY);
        Assertions.assertThat(foundDayShifts.size()).isEqualTo(expectedNumberOfDayShifts);

        List<Shift> foundNightShifts = shiftRepository.findAllByDateAndType(LocalDate.of(year, month, day), Type.NIGHT);
        Assertions.assertThat(foundNightShifts.size()).isEqualTo(expectedNumberOfNightShifts);

    }

    @ParameterizedTest
    @CsvSource({
            "0, 3",
            "1, 3",
            "2, 2"
    })
    public void shouldFindAllShiftsByCarIdWhenShiftsConnectedToThatCarIdAreInDataset(int carIndex, int expectedNumberOfShifts)
    {
        List<Shift> foundShifts = shiftRepository.findAllByCarId(cars.get(carIndex).getId());

        Assertions.assertThat(foundShifts.size()).isEqualTo(expectedNumberOfShifts);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "1, 2",
            "2, 1"
    })
    public void shouldCountByUserIdAndIsDoneTrue(int userIndex, int expectedNumberOfDoneShifts)
    {
        Integer numberOfShifts = shiftRepository.countByUser_IdAndIsDoneTrue(users.get(userIndex).getId());

        Assertions.assertThat(numberOfShifts).isEqualTo(expectedNumberOfDoneShifts);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2023, 12, 30",
            "1, 2023, 12, 60",
            "2, 2023, 12, 60"
    })
    public void shouldGetTotalLpgByUserAndYearAndMonth(int userIndex, int year, int month, BigDecimal expectedTotalLpg)
    {
        Optional<BigDecimal> totalLpg = shiftRepository.getTotalLpgByUserAndYearAndMonth(year, month, users.get(userIndex).getId());

        Assertions.assertThat(totalLpg).isNotEmpty();
        Assertions.assertThat(totalLpg.get()).isCloseTo(expectedTotalLpg, Percentage.withPercentage(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "2023, 12, 1, 1",
            "2023, 12, 2, 1",
            "2023, 12, 3, 1",
            "2023, 12, 4, 0"
    })
    public void shouldFindAllShiftsByIsCarAvailableFalseAndDateGreaterThanEqualWhenThoseShiftsAreInDataset(int year, int month, int day, int expectedNumberOfShifts)
    {
        List<Shift> foundShifts = shiftRepository.findByIsCarAvailableFalseAndDateGreaterThanEqual(LocalDate.of(year, month, day), 1);

        Assertions.assertThat(foundShifts.size()).isEqualTo(expectedNumberOfShifts);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 2",
            "1, 1",
            "2, 1"
    })
    public void shouldFindAllUnstartedAndUndoneInFutureShiftsWhenThoseShiftsAreInDataset(int carIndex, int expectedNumberOfShifts)
    {
        shiftRepository.deleteAll();

        loadUnstartedAndUndoneShifts();

        List<Shift> foundShifts = shiftRepository.findUnstartedAndUndoneShifts(cars.get(carIndex).getId());

        Assertions.assertThat(foundShifts.size()).isEqualTo(expectedNumberOfShifts);
    }

    private void loadUnstartedAndUndoneShifts()
    {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.DAY).startMileage(1000).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(1)).endMileage(1100).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.DAY).startMileage(2000).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1200).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.DAY).startMileage(3000).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3300).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(2)).user(users.get(2)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.NIGHT).startMileage(1100).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1200).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.NIGHT).startMileage(1200).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1400).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(2)).type(Type.DAY).startMileage(1200).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1300).isStarted(Boolean.TRUE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(2)).type(Type.DAY).startMileage(1400).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(3)).type(Type.DAY).startMileage(3300).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(2)).user(users.get(2)).build());

        shiftRepository.saveAll(shifts);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4",
            "1, 4",
            "2, 4"
    })
    public void shouldFindAllShiftsByCarIdAndFutureDateWhenShiftsForFutureDateConnectedToThisCarIdAreInDataset(int carIndex, int expectedNumberOfShifts)
    {
        shiftRepository.deleteAll();

        loadShiftsWithUnavailableCarWithFutureDate();

        List<Shift> foundShifts = shiftRepository.findShiftsForCarAndDateFuture(cars.get(carIndex).getId());

        Assertions.assertThat(foundShifts.size()).isEqualTo(expectedNumberOfShifts);
    }

    private void loadShiftsWithUnavailableCarWithFutureDate()
    {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(Shift.builder().date(LocalDate.now().minusDays(1)).type(Type.DAY).startMileage(1000).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(1)).endMileage(1100).isStarted(Boolean.TRUE).isDone(Boolean.TRUE).isCarAvailable(Boolean.FALSE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.DAY).startMileage(2000).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1200).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(1)).user(users.get(1)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.DAY).startMileage(3000).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3300).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(2)).user(users.get(2)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.NIGHT).startMileage(1100).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1200).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(1)).type(Type.NIGHT).startMileage(1200).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1400).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(2)).type(Type.DAY).startMileage(1200).lpg(BigDecimal.valueOf(10)).petrol(BigDecimal.valueOf(10)).endMileage(1300).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(0)).user(users.get(0)).build());
        shifts.add(Shift.builder().date(LocalDate.now().plusDays(2)).type(Type.DAY).startMileage(1400).lpg(BigDecimal.valueOf(20)).petrol(BigDecimal.valueOf(20)).endMileage(1600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.TRUE).car(cars.get(1)).user(users.get(1)).build());

        shifts.add(Shift.builder().date(LocalDate.now().plusDays(3)).type(Type.DAY).startMileage(3300).lpg(BigDecimal.valueOf(30)).petrol(BigDecimal.valueOf(30)).endMileage(3600).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).isCarAvailable(Boolean.FALSE).car(cars.get(2)).user(users.get(2)).build());

        shiftRepository.saveAll(shifts);
    }

    @Test
    public void shouldFindShiftByIdWhenShiftWithThisIdIsInDatabase()
    {
        long firstId = shiftRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfShifts;
        for (long i = firstId; i < lastId; ++i)
        {
            Optional<Shift> foundShift = shiftRepository.findById(i);

            Assertions.assertThat(foundShift).isNotEmpty();
        }
    }

    @Test
    public void shouldNotFindShiftByIdWhenShiftWithThisIdIsNotInDatabase()
    {
        for (long i = 999L; i < numberOfShifts; ++i)
        {
            Optional<Shift> foundShift = shiftRepository.findById(i);

            Assertions.assertThat(foundShift).isEmpty();
        }
    }

    @Test
    public void shouldDeleteShiftByIdWhenShiftWithThisIsInDatabase()
    {
        long firstId = shiftRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfShifts;
        for (long i = firstId; i < lastId; ++i)
        {
            shiftRepository.deleteById(i);
            Optional<Shift> foundShift = shiftRepository.findById(i);

            Assertions.assertThat(foundShift).isEmpty();
        }
    }
}
