package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementIncompleteException extends RuntimeException
{
    public Object[] properties;

    public SettlementIncompleteException(String firstName, String lastName, Integer month, Integer year)
    {
        properties = new Object[]{firstName, lastName, month, year};
    }
}
