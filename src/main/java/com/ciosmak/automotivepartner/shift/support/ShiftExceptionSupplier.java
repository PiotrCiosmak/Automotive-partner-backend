package com.ciosmak.automotivepartner.shift.support;

import com.ciosmak.automotivepartner.shift.support.exception.ShiftNotFoundException;

import java.util.function.Supplier;

public class ShiftExceptionSupplier
{
    public static Supplier<ShiftNotFoundException> shiftNotFound(Long id)
    {
        return () -> new ShiftNotFoundException(id);
    }
}
