package com.ciosmak.automotivepartner.shift.support.exception;

public class IncorrectLpgConsumptionException extends RuntimeException
{
    public IncorrectLpgConsumptionException()
    {
        super("Podaj poprawnie ile litrów gazu spaliło auto.");
    }
}
