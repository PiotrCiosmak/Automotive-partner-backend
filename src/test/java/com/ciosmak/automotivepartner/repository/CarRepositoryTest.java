package com.ciosmak.automotivepartner.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.support.Type;
import com.ciosmak.automotivepartner.user.domain.User;
import com.ciosmak.automotivepartner.user.support.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
public class CarRepositoryTest
{
    private static int numberOfCars;
    @Autowired
    private CarRepository carRepository;

    void loadUnAvailableCars()
    {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().registrationNumber("AB12345").mileage(1000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("CD2345").mileage(2000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("EF12345").mileage(3000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("GH12345").mileage(4000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("IJ12345").mileage(5000).isBlocked(Boolean.TRUE).build());
        carRepository.saveAll(cars);
    }

    @BeforeEach
    public void setUp()
    {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().registrationNumber("AA12345").mileage(1000).isBlocked(Boolean.FALSE).build());
        cars.add(Car.builder().registrationNumber("BB12345").mileage(2000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("CC12345").mileage(3000).isBlocked(Boolean.FALSE).build());
        cars.add(Car.builder().registrationNumber("DD12345").mileage(4000).isBlocked(Boolean.TRUE).build());
        cars.add(Car.builder().registrationNumber("EE12345").mileage(5000).isBlocked(Boolean.FALSE).build());
        carRepository.saveAll(cars);
        numberOfCars = cars.size();
    }

    @Test
    public void shouldSaveCar()
    {
        Car savedCar = carRepository.save(Car.builder().registrationNumber("AA54321").mileage(0).isBlocked(Boolean.FALSE).build());

        Assertions.assertThat(savedCar).isNotNull();
        Assertions.assertThat(savedCar.getRegistrationNumber()).isEqualTo("AA54321");
        Assertions.assertThat(savedCar.getMileage()).isEqualTo(0);
        Assertions.assertThat(savedCar.getIsBlocked()).isEqualTo(false);
    }

    @ParameterizedTest
    @ValueSource(strings = {"AA12345", "BB12345", "CC12345", "DD12345", "EE12345"})
    public void shouldFindCarByRegistrationNumberWhenCarWithThatRegistrationNumberIsInDatabase(String expectedRegistrationNumber)
    {
        Optional<Car> foundCar = carRepository.findByRegistrationNumber(expectedRegistrationNumber);

        Assertions.assertThat(foundCar).isNotEmpty();
        Assertions.assertThat(foundCar.get().getRegistrationNumber()).isEqualTo(expectedRegistrationNumber);
    }

    @Test
    public void shouldNotFindCarByRegistrationNumberWhenCarWithThatRegistrationNumberIsNotInDatabase()
    {
        Optional<Car> foundCar = carRepository.findByRegistrationNumber("YY99999");

        Assertions.assertThat(foundCar).isEmpty();
    }

    @Test
    public void shouldFindAllCarsWhenCarsAreInDatabase()
    {
        List<Car> foundedCars = carRepository.findAll();

        Assertions.assertThat(foundedCars.size()).isEqualTo(numberOfCars);
        for (var car : foundedCars)
        {
            Assertions.assertThat(car).isNotNull();
        }
    }

    @Test
    public void shouldFindZeroCarsWhenCarsAreNotInDatabase()
    {
        carRepository.deleteAll();

        List<Car> cars = carRepository.findAll();

        Assertions.assertThat(cars.size()).isEqualTo(0);
    }

    @Test
    public void shouldFindAllUnblockedCarsWhenUnblockedCarsAreInDataset()
    {
        List<Car> foundedCars = carRepository.findAllByIsBlocked(Boolean.FALSE);

        Assertions.assertThat(foundedCars.size()).isEqualTo(3);
        for (var car : foundedCars)
        {
            Assertions.assertThat(car).isNotNull();
            Assertions.assertThat(car.getIsBlocked()).isEqualTo(Boolean.FALSE);
        }
    }

    @Test
    public void shouldFindAllBlockedCarsWhenBlockedCarsAreInDataset()
    {
        List<Car> foundedCars = carRepository.findAllByIsBlocked(Boolean.TRUE);

        Assertions.assertThat(foundedCars.size()).isEqualTo(2);
        for (var car : foundedCars)
        {
            Assertions.assertThat(car).isNotNull();
            Assertions.assertThat(car.getIsBlocked()).isEqualTo(Boolean.TRUE);
        }
    }

    @Test
    public void shouldReturnTrueWhenCarIsBlocked()
    {
        List<Car> foundedCars = carRepository.findAllByIsBlocked(Boolean.TRUE);

        for (var car : foundedCars)
        {
            Assertions.assertThat(car).isNotNull();
            Assertions.assertThat(carRepository.isBlocked(car.getId())).isEqualTo(Boolean.TRUE);
        }
    }

    @Test
    public void shouldReturnFalseWhenCarIsUnblocked()
    {
        List<Car> foundedCars = carRepository.findAllByIsBlocked(Boolean.FALSE);

        for (var car : foundedCars)
        {
            Assertions.assertThat(car).isNotNull();
            Assertions.assertThat(carRepository.isBlocked(car.getId())).isEqualTo(Boolean.FALSE);
        }
    }

    @Test
    public void shouldFindAvailableCarsForShiftWhenSomeCarIsAvailable()
    {
        User user = User.builder().firstName("Test").lastName("Test").email("test@example.com").password("Test123_").phoneNumber("123456789").role(Role.DRIVER).build();

        Shift shift = Shift.builder().date(LocalDate.now()).type(Type.DAY).isStarted(Boolean.FALSE).isDone(Boolean.FALSE).user(user).isCarAvailable(Boolean.FALSE).build();
        List<Car> foundCars = carRepository.findAvailableCarsForShift(shift.getDate(), shift.getType());

        Assertions.assertThat(foundCars).isNotEmpty();
    }

    @Test
    public void shouldNotFindAvailableCarsForShiftWhenAnyCarIsNotAvailable()
    {
        carRepository.deleteAll();
        loadUnAvailableCars();

        List<Car> foundCars = carRepository.findAvailableCarsForShift(LocalDate.now(), Type.DAY);

        Assertions.assertThat(foundCars).isEmpty();
    }

    @Test
    public void shouldDeleteCarByIdWhenCarWithThisIsInDatabase()
    {
        long firstId = carRepository.findAll().get(0).getId();
        long lastId = firstId + numberOfCars;
        for (long i = firstId; i < lastId; ++i)
        {
            carRepository.deleteById(i);
            Optional<Car> foundCar = carRepository.findById(i);

            Assertions.assertThat(foundCar).isEmpty();
        }
    }
}
