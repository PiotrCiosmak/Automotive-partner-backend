package com.ciosmak.automotivepartner.shift.support.exception;

public class IncorrectPetrolConsumptionException extends RuntimeException
{
    public IncorrectPetrolConsumptionException()
    {
        super("Podaj poprawnie ile litrów benzyny spaliło auto.");
    }
}
