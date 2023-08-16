package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotFoundException extends RuntimeException
{
    public ShiftNotFoundException(Long id)
    {
        super(String.format("Brak zmiany o id %d.", id));
    }
}
