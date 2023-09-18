package com.ciosmak.automotivepartner.shift.support;

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

    public static Supplier<ShiftAlreadyDoneException> shiftAlreadyDone(Long id)
    {
        return () -> new ShiftAlreadyDoneException(id);
    }

    public static Supplier<ShiftAlreadyStartedException> shiftAlreadyStarted(Long id)
    {
        return () -> new ShiftAlreadyStartedException(id);
    }

    public static Supplier<ShiftNotDoneException> shiftNotDone(Long id)
    {
        return () -> new ShiftNotDoneException(id);
    }

    public static Supplier<ShiftNotFoundException> shiftNotFound(Long id)
    {
        return () -> new ShiftNotFoundException(id);
    }

    public static Supplier<ShiftNotStartedException> shiftNotStarted(Long id)
    {
        return () -> new ShiftNotStartedException(id);
    }

    public static Supplier<ShiftsAlreadyGeneratedException> shiftsAlreadyGenerated()
    {
        return ShiftsAlreadyGeneratedException::new;
    }

    public static Supplier<ShiftsGeneratingTooEarlyException> shiftsGeneratingTooEarly()
    {
        return ShiftsGeneratingTooEarlyException::new;
    }

    public static Supplier<ShiftStartException> shiftStart(Long userId, LocalDate date, Type type)
    {
        return () -> new ShiftStartException(userId, date, type);
    }
}
