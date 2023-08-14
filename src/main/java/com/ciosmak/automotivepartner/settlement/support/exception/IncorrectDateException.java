package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectDateException extends RuntimeException
{
    public IncorrectDateException()
    {
        super("Podaj poprawną datę.");
    }
}
