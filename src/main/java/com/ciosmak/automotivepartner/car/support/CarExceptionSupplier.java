package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.support.exception.*;

import java.util.function.Supplier;

public class CarExceptionSupplier
{
    public static Supplier<CarAlreadyBlockedException> carAlreadyBlocked()
    {
        return CarAlreadyBlockedException::new;
    }

    public static Supplier<CarNotBlockedException> carNotBlocked()
    {
        return CarNotBlockedException::new;
    }

    public static Supplier<CarNotFoundException> carNotFound(Long id)
    {
        return () -> new CarNotFoundException(id);
    }

    public static Supplier<EmptyMileageException> emptyMileage()
    {
        return EmptyMileageException::new;
    }

    public static Supplier<EmptyRegistrationNumberException> emptyRegistrationNumber()
    {
        return EmptyRegistrationNumberException::new;
    }

    public static Supplier<IncorrectMileageException> incorrectMileage()
    {
        return IncorrectMileageException::new;
    }

    public static Supplier<IncorrectRegistrationNumberException> incorrectRegistrationNumber()
    {
        return IncorrectRegistrationNumberException::new;
    }

    public static Supplier<RegistrationNumberTakenException> registrationNumberTaken(String registrationNumber)
    {
        return () -> new RegistrationNumberTakenException(registrationNumber);
    }
}
