package com.ciosmak.automotivepartner.settlement.support.exception;

public class EmptyNetAmountException extends RuntimeException
{
    public EmptyNetAmountException()
    {
        super("Podaj kwotę netto.");
    }
}
