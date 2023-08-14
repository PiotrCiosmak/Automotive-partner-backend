package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectOptionalDateException extends RuntimeException
{
    public IncorrectOptionalDateException()
    {
        super("Podaj poprawną datę lub pozostaw domyślną.");
    }
}
