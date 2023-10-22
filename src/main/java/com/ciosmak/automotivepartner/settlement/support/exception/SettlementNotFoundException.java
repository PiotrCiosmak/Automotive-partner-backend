package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementNotFoundException extends RuntimeException
{
    public Object[] properties;

    public SettlementNotFoundException(Long id)
    {
        properties = new Object[]{id};
    }
}
