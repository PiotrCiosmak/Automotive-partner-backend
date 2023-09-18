package com.ciosmak.automotivepartner.shift.support;

import com.ciosmak.automotivepartner.availability.support.Type;
import com.ciosmak.automotivepartner.shared.exception.EmptyMileageException;
import com.ciosmak.automotivepartner.shared.exception.IncorrectMileageException;
import com.ciosmak.automotivepartner.shift.support.exception.*;

import java.time.LocalDate;
import java.util.function.Supplier;

public class ShiftExceptionSupplier
{
    public static Supplier<EmptyInvoicePhotoException> emptyInvoicePhoto()
    {
        return EmptyInvoicePhotoException::new;
    }

    public static Supplier<EmptyLpgConsumptionException> emptyLpgConsumption()
    {
        return EmptyLpgConsumptionException::new;
    }

    public static Supplier<EmptyMileagePhotoException> emptyMileagePhoto()
    {
        return EmptyMileagePhotoException::new;
    }

    public static Supplier<EmptyPetrolConsumptionException> emptyPetrolConsumption()
    {
        return EmptyPetrolConsumptionException::new;
    }

    public static Supplier<EmptyMileageException> emptyMileage()
    {
        return EmptyMileageException::new;
    }

    public static Supplier<IncorrectMileageException> incorrectMileage()
    {
        return IncorrectMileageException::new;
    }

    public static Supplier<IncorrectLpgConsumptionException> incorrectLpgConsumption()
    {
        return IncorrectLpgConsumptionException::new;
    }

    public static Supplier<IncorrectPetrolConsumptionException> incorrectPetrolConsumption()
    {
        return IncorrectPetrolConsumptionException::new;
    }

    public static Supplier<NotEnoughPhotosOfCarFromOutsideException> notEnoughPhotosOfCarFromOutside()
    {
        return NotEnoughPhotosOfCarFromOutsideException::new;
    }

    public static Supplier<ShiftNotCompletedException> shiftNotCompleted(Long id)
    {
        return () -> new ShiftNotCompletedException(id);
    }

    public static Supplier<ShiftNotFoundException> shiftNotFound(Long id)
    {
        return () -> new ShiftNotFoundException(id);
    }

    public static Supplier<ShiftStartException> shiftStart(Long userId, LocalDate date, Type type, Long carId)
    {
        return () -> new ShiftStartException(userId, date, type, carId);
    }
}
