package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectTipAmountException extends RuntimeException
{
    public IncorrectTipAmountException()
    {
        super("Podaj poprawną kwotę napiwków.");
    }
}
