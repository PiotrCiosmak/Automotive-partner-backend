package com.ciosmak.automotivepartner.statistic.support.exception;

public class IncorrectDateException extends RuntimeException
{
    public IncorrectDateException()
    {
        super("Podaj poprawną datę.");
    }
}
