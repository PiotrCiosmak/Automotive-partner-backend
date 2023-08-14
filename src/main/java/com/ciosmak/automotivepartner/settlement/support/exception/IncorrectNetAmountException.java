package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectNetAmountException extends RuntimeException
{
    public IncorrectNetAmountException()
    {
        super("Podaj poprawną kwotę netto.");
    }
}
