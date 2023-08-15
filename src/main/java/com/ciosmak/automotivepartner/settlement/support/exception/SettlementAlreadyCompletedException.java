package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementAlreadyCompletedException extends RuntimeException
{
    public SettlementAlreadyCompletedException(String firstName, String lastName, Integer month, Integer year)
    {
        super(String.format("Rozliczenie dla %s %s w %d-%d zostało już  uzupełnione. Możesz je tylko zaktualizować.", firstName, lastName, month, year));
    }
}
