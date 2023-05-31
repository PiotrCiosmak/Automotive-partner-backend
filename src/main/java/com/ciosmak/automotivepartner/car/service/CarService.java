package com.ciosmak.automotivepartner.car.service;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.car.support.CarMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService
{
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    //TODO lombok
    public CarService(CarRepository carRepository, CarMapper carMapper)
    {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public CarResponse create(CarRequest carRequest)
    {
        Car car = carRepository.save(carMapper.toCar(carRequest));
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

    public CarResponse update(UpdateCarRequest updateCarRequest)
    {
        Car car = carRepository.findById(updateCarRequest.getId()).orElseThrow(CarExceptionSupplier.carNotFound(updateCarRequest.getId()));
        carRepository.save(carMapper.toCar(car, updateCarRequest));
        return carMapper.toCarResponse(car);
    }

    public void delete(Long id)
    {
        Car car = carRepository.findById(id).orElseThrow(CarExceptionSupplier.carNotFound(id));
        carRepository.deleteById(car.getId());
    }
}
