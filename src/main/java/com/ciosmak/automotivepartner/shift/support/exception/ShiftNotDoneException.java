package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotDoneException extends RuntimeException
{
    public ShiftNotDoneException(Long id)
    {
        super(String.format("Zmiana od id %d nie została jeszcze zakończona.", id));
    }
}
