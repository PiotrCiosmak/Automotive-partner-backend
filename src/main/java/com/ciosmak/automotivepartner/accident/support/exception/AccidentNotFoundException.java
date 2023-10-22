package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentNotFoundException extends RuntimeException
{
    public Object[] properties;

    public AccidentNotFoundException(Long id)
    {
        properties = new Object[]{id};
    }
}
