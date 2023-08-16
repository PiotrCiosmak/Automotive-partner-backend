package com.ciosmak.automotivepartner.shift.support.exception;

public class EmptyLpgConsumptionException extends RuntimeException
{
    public EmptyLpgConsumptionException()
    {
        super("Podaj ile litrów gazu spaliło auta.");
    }
}
