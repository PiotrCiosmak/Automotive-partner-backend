package com.ciosmak.automotivepartner.settlement.support.exception;

public class IncorrectOptionalPenaltyAmountException extends RuntimeException
{
    public IncorrectOptionalPenaltyAmountException()
    {
        super("Podaj poprawną kwotę kary lub pozostaw domyślną.");
    }
}
