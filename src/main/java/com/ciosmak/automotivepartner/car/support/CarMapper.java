package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.request.UpdateCarMileageRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper
{
    public Car toCar(CarRequest carRequest)
    {
        return new Car(carRequest.getRegistrationNumber().toUpperCase().replace(" ", ""), carRequest.getMileage(), carRequest.getBlocked());
    }

    public Car toCar(Car car, UpdateCarMileageRequest carRequest)
    {
        car.setMileage(carRequest.getMileage());
        return car;
    }

    public Car toCar(Car car, CarRequest carRequest)
    {
        car.setRegistrationNumber(carRequest.getRegistrationNumber().toUpperCase());
        car.setMileage(carRequest.getMileage());
        car.setBlocked(carRequest.getBlocked());
        return car;
    }

    public CarResponse toCarResponse(Car car)
    {
        return new CarResponse(car.getId(), car.getRegistrationNumber().toUpperCase().replace(" ", ""), car.getMileage(), car.getBlocked());
    }
}
