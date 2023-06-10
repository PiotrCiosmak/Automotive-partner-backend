package com.ciosmak.automotivepartner.car.service;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.car.support.CarMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarService
{
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarResponse add(CarRequest carRequest)
    {
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

    public CarResponse update(Long id, UpdateCarRequest updateCarRequest)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        carRepository.save(carMapper.toCar(car, updateCarRequest));
        return carMapper.toCarResponse(car);
    }

    public void delete(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        carRepository.deleteById(car.getId());
    }
}
