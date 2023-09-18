package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftAlreadyDoneException extends RuntimeException
{
    public ShiftAlreadyDoneException(Long id)
    {
        super(String.format("Zmiana od id %d została już zakończona.", id));
    }
}
