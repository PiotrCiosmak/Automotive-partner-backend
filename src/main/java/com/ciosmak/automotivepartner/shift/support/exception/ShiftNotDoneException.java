package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotDoneException extends RuntimeException
{
    public Object[] properties;

    public ShiftNotDoneException(Long id)
    {
        properties = new Object[]{id};
    }
}
