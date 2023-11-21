package com.ciosmak.automotivepartner.car.service;

import com.ciosmak.automotivepartner.availability.domain.Availability;
import com.ciosmak.automotivepartner.availability.repository.AvailabilityRepository;
import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarMileageRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.car.support.CarMapper;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.service.ShiftService;
import com.ciosmak.automotivepartner.shift.support.Type;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarService
{
    private final CarRepository carRepository;
    private final AvailabilityRepository availabilityRepository;
    private final CarMapper carMapper;
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;

    @Transactional
    public CarResponse add(CarRequest carRequest)
    {
        checkIfCarDataAreCorrect(carRequest);

        Car car = carRepository.save(carMapper.toCar(carRequest));

        return carMapper.toCarResponse(car);
    }

    private void checkIfCarDataAreCorrect(CarRequest carRequest)
    {
        String registrationNumber = carRequest.getRegistrationNumber().toUpperCase().replace(" ", "");
        checkIfRegistrationNumberIsCorrect(registrationNumber);

        Integer mileage = carRequest.getMileage();
        checkIfMileageIsCorrect(mileage);
    }

    private void checkIfRegistrationNumberIsCorrect(String registrationNumber)
    {
        if (registrationNumber.isEmpty())
        {
            throw CarExceptionSupplier.emptyRegistrationNumber().get();
        }
        if (isRegistrationNumberIncorrect(registrationNumber))
        {
            throw CarExceptionSupplier.incorrectRegistrationNumber().get();
        }
        if (isRegistrationNumberTaken(registrationNumber))
        {
            throw CarExceptionSupplier.registrationNumberTaken(registrationNumber).get();
        }
    }

    private boolean isRegistrationNumberIncorrect(String registrationNumber)
    {
        String patternI = "[A-Za-z]{2} ?\\d{5}";
        String patternII = "[A-Za-z]{2} ?\\d{4}[A-Za-z]";
        String patternIII = "[A-Za-z]{2} ?\\d{3}[A-Za-z]{2}";
        String patternIV = "[A-Za-z]{2} ?[1-9][A-Za-z]\\d{3}";
        String patternV = "[A-Za-z]{2} ?[1-9][A-Za-z]{2}\\d{2}";
        String patternVI = "[A-Za-z]{3} ?[A-Za-z]\\d{2}[1-9]";
        String patternVII = "[A-Za-z]{3} ?\\d[1-9][A-Za-z]{2}";
        String patternVIII = "[A-Za-z]{3} ?[1-9][A-Za-z]\\d[1-9]";
        String patternIX = "[A-Za-z]{3} ?\\d[1-9][A-Za-z]\\d";
        String patternX = "[A-Za-z]{3} ?[1-9][A-Za-z]{2}[1-9]";
        String patternXI = "[A-Za-z]{3} ?[A-Za-z]{2}\\d[1-9]";
        String patternXII = "[A-Za-z]{3} ?\\d{4}[1-9]";
        String patternXIII = "[A-Za-z]{3} ?\\d{3}[1-9][A-Za-z]";
        String patternXIV = "[A-Za-z]{3} ?\\d{2}[1-9][A-Za-z]{2}";
        String patternXV = "[A-Za-z]{3} ?[A-Za-z]\\d[1-9][A-Za-z]";
        String patternXVI = "[A-Za-z]{3} ?[A-Za-z][1-9][A-Za-z]{2}";

        return !registrationNumber.matches(patternI) && !registrationNumber.matches(patternII) && !registrationNumber.matches(patternIII) && !registrationNumber.matches(patternIV) && !registrationNumber.matches(patternV) && !registrationNumber.matches(patternVI) && !registrationNumber.matches(patternVII) && !registrationNumber.matches(patternVIII) && !registrationNumber.matches(patternIX) && !registrationNumber.matches(patternX) && !registrationNumber.matches(patternXI) && !registrationNumber.matches(patternXII) && !registrationNumber.matches(patternXIII) && !registrationNumber.matches(patternXIV) && !registrationNumber.matches(patternXV) && !registrationNumber.matches(patternXVI);
    }

    private boolean isRegistrationNumberTaken(String registrationNumber)
    {
        return carRepository.findByRegistrationNumber(registrationNumber).isPresent();
    }

    private void checkIfMileageIsCorrect(Integer mileage)
    {
        if (mileage == null)
        {
            throw CarExceptionSupplier.emptyMileage().get();
        }
        if (isMileageIncorrect(mileage))
        {
            throw CarExceptionSupplier.incorrectMileage().get();
        }
    }

    private boolean isMileageIncorrect(Integer mileage)
    {
        return mileage < 0;
    }

    @Transactional
    public CarResponse block(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        boolean carIsBlocked = carRepository.isBlocked(id);
        if (carIsBlocked)
        {
            throw CarExceptionSupplier.carAlreadyBlocked().get();
        }
        car.setIsBlocked(Boolean.TRUE);

        List<Shift> shifts = shiftRepository.findUnstartedAndUndoneShifts(car.getId());
        for (var shift : shifts)
        {
            List<Car> availableCars = carRepository.findAvailableCarsForShift(shift.getDate(), shift.getType());
            if (availableCars.isEmpty())
            {
                shift.setIsCarAvailable(Boolean.FALSE);
            }
            else
            {
                Car availableCar = availableCars.get(0);
                shift.setCar(availableCar);
            }
        }

        return carMapper.toCarResponse(car);
    }

    @Transactional
    public CarResponse unblock(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        boolean carIsBlocked = carRepository.isBlocked(id);
        if (!carIsBlocked)
        {
            throw CarExceptionSupplier.carNotBlocked().get();
        }
        car.setIsBlocked(Boolean.FALSE);

        List<Shift> shifts = shiftRepository.findShiftsForCarAndDateFuture(car.getId());
        for (var shift : shifts)
        {
            List<Car> availableCars = carRepository.findAvailableCarsForShift(shift.getDate(), shift.getType());
            if (availableCars.isEmpty())
            {
                shift.setIsCarAvailable(Boolean.TRUE);
            }
            else
            {
                shift.setCar(availableCars.get(0));
                shift.setIsCarAvailable(Boolean.TRUE);
            }
        }

        List<Availability> availabilities = availabilityRepository.findAllByIsUsedFalseAndDateFuture();

        Map<Pair<LocalDate, Type>, List<Availability>> dateToAvailabilities = availabilities.stream()
                .collect(Collectors.groupingBy(
                        availability -> Pair.of(availability.getDate(), availability.getType()),
                        Collectors.toList()
                ));

        for (Map.Entry<Pair<LocalDate, Type>, List<Availability>> entry : dateToAvailabilities.entrySet())
        {
            LocalDate date = entry.getKey().getLeft();
            Type type = entry.getKey().getRight();
            List<Availability> availabilitiesActualDateAndType = entry.getValue();

            List<Long> userIds = new ArrayList<>();
            for (var availability : availabilitiesActualDateAndType)
            {
                userIds.add(availability.getUser().getId());
            }

            int numberOfPotentialShifts = userIds.size();
            List<Car> availableCars = carRepository.findAvailableCarsForShift(date, type);
            int numberOfPossibleShiftsToBeGenerated = Math.min(numberOfPotentialShifts, availableCars.size());

            for (int i = 0; i < numberOfPossibleShiftsToBeGenerated; ++i)
            {
                shiftService.generateShift(date, type, car.getId(), userIds.get(i));
                availabilitiesActualDateAndType.get(i).setIsUsed(true);
            }
        }

        return carMapper.toCarResponse(car);
    }

    @Transactional
    public CarResponse updateMileage(UpdateCarMileageRequest updateCarMileageRequest)
    {
        Long id = updateCarMileageRequest.getId();
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));

        Integer mileage = updateCarMileageRequest.getMileage();
        checkIfMileageIsCorrect(mileage);

        car = carMapper.toCar(car, updateCarMileageRequest);

        return carMapper.toCarResponse(car);
    }

    public List<CarResponse> findAll(String filterText)
    {
        List<Car> cars = carRepository.findAll();
        return getFilteredCars(cars, filterText);
    }

    public List<CarResponse> findAllUnblocked(String filterText)
    {
        List<Car> cars = carRepository.findAllByIsBlocked(Boolean.FALSE);
        return getFilteredCars(cars, filterText);
    }

    public List<CarResponse> findAllBlocked(String filterText)
    {
        List<Car> cars = carRepository.findAllByIsBlocked(Boolean.TRUE);
        return getFilteredCars(cars, filterText);
    }

    private List<CarResponse> getFilteredCars(List<Car> cars, String filterText)
    {
        if (filterText.isEmpty())
        {
            return cars.stream().map(carMapper::toCarResponse).collect(Collectors.toList());
        }
        return cars.stream().filter(car -> car.getRegistrationNumber().toLowerCase().contains(filterText.toLowerCase())).map(carMapper::toCarResponse).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        checkIfCarIsNotAssignedToAnyShifty(shiftRepository.findAllByCarId(car.getId()));
        carRepository.deleteById(car.getId());
    }

    private void checkIfCarIsNotAssignedToAnyShifty(List<Shift> shifts)
    {
        if (!shifts.isEmpty())
        {
            throw CarExceptionSupplier.carAssignedToShift().get();
        }
    }
}
