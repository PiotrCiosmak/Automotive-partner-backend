package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftAlreadyStartedException extends RuntimeException
{
    public Object[] properties;

    public ShiftAlreadyStartedException(Long id)
    {
        properties = new Object[]{id};
    }
}
