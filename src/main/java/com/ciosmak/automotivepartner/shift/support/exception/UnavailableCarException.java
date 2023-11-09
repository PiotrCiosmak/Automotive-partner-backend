package com.ciosmak.automotivepartner.shift.support.exception;

public class UnavailableCarException extends RuntimeException
{
    public Object[] properties;

    public UnavailableCarException(Long id)
    {
        properties = new Object[]{id};
    }
}
