package com.ciosmak.automotivepartner.statistic.support.exception;

public class IncorrectDateException extends RuntimeException
{
    public IncorrectDateException(String firstName, String lastName, Integer month, Integer year)
    {
        super(String.format("Statystyki dla %s %s w %d-%d nie istnieją.", firstName, lastName, month, year));
    }
}
