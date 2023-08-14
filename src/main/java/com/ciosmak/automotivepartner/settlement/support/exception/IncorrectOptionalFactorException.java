package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectOptionalFactorException extends RuntimeException
{
    public IncorrectOptionalFactorException()
    {
        super("Podaj poprawny współczynnik lub pozostaw domyślny.");
    }
}
