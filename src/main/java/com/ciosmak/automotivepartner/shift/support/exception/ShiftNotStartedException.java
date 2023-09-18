package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftNotStartedException extends RuntimeException
{
    public ShiftNotStartedException(Long id)
    {
        super(String.format("Zmiana o %d nie została jeszcze rozpoczęta.", id));
    }
}
