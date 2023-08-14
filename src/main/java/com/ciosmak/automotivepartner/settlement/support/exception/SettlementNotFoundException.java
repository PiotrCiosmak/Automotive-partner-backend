package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementNotFoundException extends RuntimeException
{
    public SettlementNotFoundException(Long id)
    {
        super(String.format("Brak rozliczenia o id %d.", id));
    }
}
