package com.ciosmak.automotivepartner.shift.support;

import com.ciosmak.automotivepartner.car.repository.CarRepository;
import com.ciosmak.automotivepartner.car.support.CarExceptionSupplier;
import com.ciosmak.automotivepartner.photo.domain.Photo;
import com.ciosmak.automotivepartner.photo.repository.PhotoRepository;
import com.ciosmak.automotivepartner.photo.response.PhotoResponse;
import com.ciosmak.automotivepartner.photo.support.PhotoMapper;
import com.ciosmak.automotivepartner.photo.support.PhotoType;
import com.ciosmak.automotivepartner.shift.api.request.*;
import com.ciosmak.automotivepartner.shift.api.response.ExtendedShiftResponse;
import com.ciosmak.automotivepartner.shift.api.response.ShiftResponse;
import com.ciosmak.automotivepartner.shift.domain.Shift;
import com.ciosmak.automotivepartner.user.repository.UserRepository;
import com.ciosmak.automotivepartner.user.support.UserExceptionSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ShiftMapper
{
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public Shift toShift(ShiftRequest shiftRequest)
    {
        return new Shift(shiftRequest.getDate(), shiftRequest.getType(), shiftRequest.getStartMileage(), shiftRequest.getLpg(), shiftRequest.getPetrol(), shiftRequest.getEndMileage(), shiftRequest.getIsStarted(), shiftRequest.getIsDone(), carRepository.findById(shiftRequest.getCarId()).orElseThrow(CarExceptionSupplier.carNotFound(shiftRequest.getCarId())), userRepository.findById(shiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(shiftRequest.getUserId())));
    }

    public Shift toShift(GenerateShiftRequest generateShiftRequest)
    {
        return new Shift(generateShiftRequest.getDate(), generateShiftRequest.getType(), null, null, null, null, Boolean.FALSE, Boolean.FALSE, carRepository.findById(generateShiftRequest.getCarId()).orElseThrow(CarExceptionSupplier.carNotFound(generateShiftRequest.getCarId())), userRepository.findById(generateShiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(generateShiftRequest.getUserId())));
    }

    public Shift toShift(Shift shift, StartShiftRequest startShiftRequest)
    {
        shift.setStartMileage(startShiftRequest.getStartMileage());
        shift.setIsStarted(Boolean.TRUE);
        return shift;
    }

    public Shift toShift(Shift shift, EndShiftRequest endShiftRequest)
    {
        shift.setLpg(endShiftRequest.getLpg());
        shift.setPetrol(endShiftRequest.getPetrol());
        shift.setEndMileage(endShiftRequest.getEndMileage());
        shift.setIsDone(Boolean.TRUE);
        return shift;
    }

    public Shift toShift(Shift shift, UpdateFuelRequest updateFuelRequest)
    {
        shift.setLpg(updateFuelRequest.getLpg());
        shift.setPetrol(updateFuelRequest.getPetrol());
        return shift;
    }

    public Shift toShift(Shift shift, ShiftRequest shiftRequest)
    {
        shift.setDate(shiftRequest.getDate());
        shift.setType(shiftRequest.getType());
        shift.setStartMileage(shiftRequest.getStartMileage());
        shift.setLpg(shiftRequest.getLpg());
        shift.setPetrol(shiftRequest.getPetrol());
        shift.setEndMileage(shiftRequest.getEndMileage());
        shift.setIsStarted(shiftRequest.getIsStarted());
        shift.setIsDone(shiftRequest.getIsDone());
        shift.setCar(carRepository.findById(shiftRequest.getCarId()).orElseThrow(CarExceptionSupplier.carNotFound(shiftRequest.getCarId())));
        shift.setUser(userRepository.findById(shiftRequest.getUserId()).orElseThrow(UserExceptionSupplier.userNotFound(shiftRequest.getUserId())));
        return shift;
    }

    public ExtendedShiftResponse toExtendedShiftResponse(Shift shift)
    {

        List<Photo> startShiftPhotos = photoRepository.findByShiftIdAndType(shift.getId(), PhotoType.SHIFT_START);
        List<PhotoResponse> startShiftPhotosResponses = new ArrayList<>();
        for (var startShiftPhoto : startShiftPhotos)
        {
            startShiftPhotosResponses.add(photoMapper.toPhotoResponse(startShiftPhoto));
        }

        List<Photo> endShiftPhotos = photoRepository.findByShiftIdAndType(shift.getId(), PhotoType.SHIFT_END);
        List<PhotoResponse> endShiftPhotosResponses = new ArrayList<>();
        for (var endShiftPhoto : endShiftPhotos)
        {
            endShiftPhotosResponses.add(photoMapper.toPhotoResponse(endShiftPhoto));
        }


        return new ExtendedShiftResponse(shift.getUser().getFirstName(), shift.getUser().getLastName(), shift.getCar().getRegistrationNumber(), shift.getStartMileage(), shift.getEndMileage(), shift.getEndMileage() - shift.getStartMileage(), shift.getLpg(), shift.getPetrol(), startShiftPhotosResponses, endShiftPhotosResponses, photoMapper.toPhotoResponse(photoRepository.findByShiftIdAndType(shift.getId(), PhotoType.INVOICE).get(0)), Boolean.FALSE);
    }//TODO gdy bedzie accident zrobiony to zwracać czy był wypadek czy nie

    public ShiftResponse toShiftResponse(Shift shift)
    {
        return new ShiftResponse(shift.getId(), shift.getDate(), shift.getType(), shift.getStartMileage(), shift.getLpg(), shift.getPetrol(), shift.getEndMileage(), shift.getIsDone(), shift.getIsStarted(), shift.getCar().getId(), shift.getUser().getId());
    }
}
