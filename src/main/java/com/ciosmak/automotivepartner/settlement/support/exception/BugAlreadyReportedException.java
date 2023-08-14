package com.ciosmak.automotivepartner.settlement.support.exception;

public class BugAlreadyReportedException extends RuntimeException
{
    public BugAlreadyReportedException()
    {
        super("Błąd został już zgłoszony.");
    }
}
