package com.ciosmak.automotivepartner.accident.support;

import com.ciosmak.automotivepartner.accident.api.request.AccidentRequest;
import com.ciosmak.automotivepartner.accident.api.resposne.AccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.BaseAccidentResponse;
import com.ciosmak.automotivepartner.accident.api.resposne.ExtendedAccidentResponse;
import com.ciosmak.automotivepartner.accident.domain.Accident;
import com.ciosmak.automotivepartner.car.domain.Car;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.shift.repository.ShiftRepository;
import com.ciosmak.automotivepartner.shift.support.ShiftExceptionSupplier;
import com.ciosmak.automotivepartner.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AccidentMapper
{
    private final ShiftRepository shiftRepository;

    public Accident toAccident(AccidentRequest accidentRequest)
    {
        return new Accident(accidentRequest.getIsGuilty(), accidentRequest.getIsEndOfWork(), shiftRepository.findById(accidentRequest.getShiftId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(accidentRequest.getShiftId())));
    }

    public Accident toAccident(Accident accident, AccidentRequest accidentRequest)
    {
        accident.setIsGuilty(accidentRequest.getIsGuilty());
        accident.setShift(shiftRepository.findById(accidentRequest.getShiftId()).orElseThrow(ShiftExceptionSupplier.shiftNotFound(accidentRequest.getShiftId())));
        return accident;
    }

    public AccidentResponse toAccidentResponse(Accident accident)
    {
        return new AccidentResponse(accident.getId(), accident.getIsGuilty(), accident.getIsEndOfWork(), accident.getShift().getId());
    }

    public BaseAccidentResponse toBaseAccidentResponse(Accident accident)
    {
        Shift shift = accident.getShift();
        User user = shift.getUser();
        return new BaseAccidentResponse(accident.getId(), shift.getDate(), shift.getType(), user.getFirstName(), user.getLastName(), shift.getId());
    }

    public ExtendedAccidentResponse toAdvanceAccidentResponse(Accident accident)
    {
        Shift shift = accident.getShift();
        User user = shift.getUser();
        Car car = shift.getCar();
        return new ExtendedAccidentResponse(shift.getDate(), shift.getType(), user.getFirstName(), user.getLastName(), car.getRegistrationNumber(), accident.getIsGuilty(), shift.getId());
    }
}
