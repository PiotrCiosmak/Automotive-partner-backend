package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementAlreadyCompletedException extends RuntimeException
{
    public Object[] properties;

    public SettlementAlreadyCompletedException(String firstName, String lastName, Integer month, Integer year)
    {
        properties = new Object[]{firstName, lastName, month, year};
    }
}
