package com.ciosmak.automotivepartner.settlement.support.exception;

public class SettlementIncompleteException extends RuntimeException
{
    public SettlementIncompleteException()
    {
        super("Ten użytkownik nie ma jeszcze uzupełnionego rozliczenia w tym miesiącu. Najpierw je uzupełnij.");
    }
}
