package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.support.exception.*;

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

    public static Supplier<CarBlockedException> carBlocked(Long id)
    {
        return () -> new CarBlockedException(id);
    }

    public static Supplier<CarUnblockedException> carUnblocked(Long id)
    {
        return () -> new CarUnblockedException(id);
    }
}
