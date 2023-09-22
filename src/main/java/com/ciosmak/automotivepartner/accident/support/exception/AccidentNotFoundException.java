package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentNotFoundException extends RuntimeException
{
    public AccidentNotFoundException(Long id)
    {
        super(String.format("Brak wypadku o id %d.", id));
    }
}
