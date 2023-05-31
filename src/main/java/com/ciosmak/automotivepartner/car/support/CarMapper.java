package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.api.request.CarRequest;
import com.ciosmak.automotivepartner.car.api.response.CarResponse;
import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper
{
    public Car toCar(CarRequest carRequest)
    {
        return new Car(carRequest.getRegistrationNumber(),carRequest.getMileage(),carRequest.getBlocked());
    }

    public CarResponse toCarResponse(Car car)
    {
        return new CarResponse(car.getId(), car.getRegistrationNumber(), car.getMileage(), car.getBlocked());
    }
}
