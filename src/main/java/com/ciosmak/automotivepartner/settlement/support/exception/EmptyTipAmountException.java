package com.ciosmak.automotivepartner.settlement.support.exception;

public class EmptyTipAmountException extends RuntimeException
{
    public EmptyTipAmountException()
    {
        super("Podaj kwotę napiwków.");
    }
}
