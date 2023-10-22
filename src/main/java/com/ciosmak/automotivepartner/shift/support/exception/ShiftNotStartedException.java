package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotStartedException extends RuntimeException
{
    public Object[] properties;

    public ShiftNotStartedException(Long id)
    {
        properties = new Object[]{id};
    }
}
