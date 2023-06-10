package com.ciosmak.automotivepartner.car.service;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarMileageRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.car.support.CarMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarService
{
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarResponse add(CarRequest carRequest)
    {
        Optional<Car> existingCar = carRepository.findByRegistrationNumber(carRequest.getRegistrationNumber());
        if (existingCar.isPresent())
        {
            throw CarExceptionSupplier.registrationNumberTaken().get();
        }
        if (areCarDetailsIncorrect(carRequest))
        {
            throw CarExceptionSupplier.incorrectCarDetails().get();
        }
        Car car = carRepository.save(carMapper.toCar(carRequest));
        return carMapper.toCarResponse(car);
    }

    private boolean areCarDetailsIncorrect(CarRequest carRequest)
    {
        return carRequest.getRegistrationNumber().isEmpty() ||
                carRequest.getMileage() < 0 ||
                carRequest.getRegistrationNumber().length() > 7;
    }

    @Transactional
    public CarResponse block(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        boolean carIsBlocked = carRepository.isBlocked(id);
        if (carIsBlocked)
        {
            throw CarExceptionSupplier.carBlocked(id).get();
        }
        carRepository.setBlocked(car.getId(), Boolean.TRUE);
        return carMapper.toCarResponse(car);
    }

    @Transactional
    public CarResponse unblock(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        boolean carIsBlocked = carRepository.isBlocked(id);
        if (!carIsBlocked)
        {
            throw CarExceptionSupplier.carUnblocked(id).get();
        }
        carRepository.setBlocked(car.getId(), Boolean.FALSE);
        return carMapper.toCarResponse(car);
    }

    public CarResponse update(Long id, UpdateCarMileageRequest updateCarMileageRequest)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        carRepository.save(carMapper.toCar(car, updateCarMileageRequest));
        return carMapper.toCarResponse(car);
    }

    public CarResponse find(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        return carMapper.toCarResponse(car);
    }

    public List<CarResponse> findAll()
    {
        return carRepository.findAll().stream().map(carMapper::toCarResponse).collect(Collectors.toList());
    }

    public List<CarResponse> findAllUnblocked()
    {
        return carRepository.findAllByBlocked(Boolean.FALSE).stream().map(carMapper::toCarResponse).collect(Collectors.toList());
    }

    public List<CarResponse> findAllBlocked()
    {
        return carRepository.findAllByBlocked(Boolean.TRUE).stream().map(carMapper::toCarResponse).collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        carRepository.deleteById(car.getId());
    }
}
