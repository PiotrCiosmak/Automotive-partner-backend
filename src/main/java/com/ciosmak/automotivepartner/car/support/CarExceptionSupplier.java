package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.support.exception.CarNotFoundException;
import com.ciosmak.automotivepartner.car.support.exception.IncorrectCarDetailsException;
import com.ciosmak.automotivepartner.car.support.exception.RegistrationNumberTakenException;

import java.util.function.Supplier;

public class CarExceptionSupplier
{
    public static Supplier<CarNotFoundException> carNotFound(Long id)
    {
        return () -> new CarNotFoundException(id);
    }

    public static Supplier<IncorrectCarDetailsException> incorrectCarDetails()
    {
        return IncorrectCarDetailsException::new;
    }

    public static Supplier<RegistrationNumberTakenException> registrationNumberTaken()
    {
        return RegistrationNumberTakenException::new;
    }
}
