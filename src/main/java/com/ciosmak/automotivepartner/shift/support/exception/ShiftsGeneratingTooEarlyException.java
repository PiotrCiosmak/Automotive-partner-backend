package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftsGeneratingTooEarlyException extends RuntimeException
{
    public ShiftsGeneratingTooEarlyException()
    {
        super("Zmiany na następny tydzień będą mogły być wygenerowane dopiero w piątek. ");
    }
}
