package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotFoundException extends RuntimeException
{
    public Object[] properties;

    public ShiftNotFoundException(Long id)
    {
        properties = new Object[]{id};
    }
}
