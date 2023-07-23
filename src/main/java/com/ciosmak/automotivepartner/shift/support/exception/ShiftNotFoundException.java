package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotFoundException extends RuntimeException
{
    public ShiftNotFoundException(Long id)
    {
        super(String.format("Shift with %d id not found", id));
    }
}
