package com.ciosmak.automotivepartner.car.service;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarMapper;
import org.springframework.stereotype.Service;

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
        Car car = carRepository.findById(id).orElseThrow(RuntimeException::new);
        return carMapper.toCarResponse(car);
    }
}
