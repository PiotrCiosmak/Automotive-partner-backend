package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentAllReadyReportedException extends RuntimeException
{
    public AccidentAllReadyReportedException(Long id)
    {
        super(String.format("Accident all ready reported in shift: %d", id));
    }
}
