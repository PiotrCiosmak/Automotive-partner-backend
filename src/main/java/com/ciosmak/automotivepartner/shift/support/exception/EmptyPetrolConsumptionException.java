package com.ciosmak.automotivepartner.shift.support.exception;

public class EmptyPetrolConsumptionException extends RuntimeException
{
    public EmptyPetrolConsumptionException()
    {
        super("Podaj ile litrów benzyny spaliło auta.");
    }
}
