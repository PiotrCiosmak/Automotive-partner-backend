package com.ciosmak.automotivepartner.statistic.support.exception;

public class IncorrectYearException extends RuntimeException
{
    public IncorrectYearException(Integer year)
    {
        super(String.format("Statystyki z %d roku nie istnieją.", year));
    }
}
