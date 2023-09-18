package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftAlreadyStartedException extends RuntimeException
{
    public ShiftAlreadyStartedException(Long id)
    {
        super(String.format("Zmiana od id %d została już rozpoczęta.", id));
    }
}
