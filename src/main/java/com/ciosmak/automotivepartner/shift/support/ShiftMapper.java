package com.ciosmak.automotivepartner.shift.support;

import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.shift.api.request.ShiftRequest;
import com.ciosmak.automotivepartner.shift.api.response.ShiftResponse;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShiftMapper
{
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public Shift toShift(ShiftRequest shiftRequest)
    {
        return new Shift(shiftRequest.getDate(), shiftRequest.getType(), shiftRequest.getStartMileage(), shiftRequest.getLpg(), shiftRequest.getPetrol(), shiftRequest.getEndMileage(), shiftRequest.getIsDone(), carRepository.findById(shiftRequest.getCarId()).orElseThrow(CarExceptionSupplier.carNotFound(shiftRequest.getCarId())), userRepository.findById(shiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(shiftRequest.getUserId())));
    }

    public Shift toShift(Shift shift, ShiftRequest shiftRequest)
    {
        shift.setDate(shiftRequest.getDate());
        shift.setType(shiftRequest.getType());
        shift.setStartMileage(shiftRequest.getStartMileage());
        shift.setLpg(shiftRequest.getLpg());
        shift.setPetrol(shiftRequest.getPetrol());
        shift.setEndMileage(shiftRequest.getEndMileage());
        shift.setIsDone(shiftRequest.getIsDone());
        shift.setCar(carRepository.findById(shiftRequest.getCarId()).orElseThrow(CarExceptionSupplier.carNotFound(shiftRequest.getCarId())));
        shift.setUser(userRepository.findById(shiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(shiftRequest.getUserId())));
        return shift;
    }

    public ShiftResponse toShiftResponse(Shift shift)
    {
        return new ShiftResponse(shift.getId(), shift.getDate(), shift.getType(), shift.getStartMileage(), shift.getLpg(), shift.getPetrol(), shift.getEndMileage(), shift.getIsDone(), shift.getCar().getId(), shift.getUser().getId());
    }
}
