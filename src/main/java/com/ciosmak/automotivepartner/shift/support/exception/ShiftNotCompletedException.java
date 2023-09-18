package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotCompletedException extends RuntimeException
{
    public ShiftNotCompletedException(Long id)
    {
        super(String.format("Zmiana o id %d nie została jeszcze zakończona.", id));
    }
}
