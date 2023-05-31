package com.ciosmak.automotivepartner.car.support;

import com.ciosmak.automotivepartner.car.support.exception.CarNotFoundException;

import java.util.function.Supplier;

public class CarExceptionSupplier
{
    public static Supplier<CarNotFoundException> carNotFound(Long id)
    {
        return () -> new CarNotFoundException(id);
    }
}
