package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftCanNotBeStartedTodayException extends RuntimeException
{
    public Object[] properties;

    public ShiftCanNotBeStartedTodayException(Long id)
    {
        properties = new Object[]{id};
    }
}
