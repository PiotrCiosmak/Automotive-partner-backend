package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementAlreadyCompletedException extends RuntimeException
{
    public SettlementAlreadyCompletedException()
    {
        super("Ten użytkownik ma już uzupełnione rozliczenia w tym miesiącu. Możesz je tylko zaktualizować.");
    }
}
