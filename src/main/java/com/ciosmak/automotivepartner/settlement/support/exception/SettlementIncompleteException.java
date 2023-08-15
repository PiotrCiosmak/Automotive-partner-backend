package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementIncompleteException extends RuntimeException
{
    public SettlementIncompleteException(String firstName, String lastName, Integer month, Integer year)
    {
        super(String.format("Rozliczenie dla %s %s w %d-%d nie zostało jeszcze uzupełnione. Proszę, najpierw je uzupełnij.", firstName, lastName, month, year));
    }
}
