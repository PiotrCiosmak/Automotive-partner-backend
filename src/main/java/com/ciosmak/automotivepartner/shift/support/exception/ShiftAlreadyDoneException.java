package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftAlreadyDoneException extends RuntimeException
{
    public Object[] properties;

    public ShiftAlreadyDoneException(Long id)
    {
        properties = new Object[]{id};
    }
}
