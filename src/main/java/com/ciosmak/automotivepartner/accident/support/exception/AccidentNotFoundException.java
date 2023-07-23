package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentNotFoundException extends RuntimeException
{
    public AccidentNotFoundException(Long id)
    {
        super(String.format("Accident with %d id not found", id));
    }
}
