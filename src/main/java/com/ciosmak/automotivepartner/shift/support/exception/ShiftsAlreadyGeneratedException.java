package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftsAlreadyGeneratedException extends RuntimeException
{
    public ShiftsAlreadyGeneratedException()
    {
        super("Zmiany na następny tydzień zostały już wygenerowane.");
    }
}
