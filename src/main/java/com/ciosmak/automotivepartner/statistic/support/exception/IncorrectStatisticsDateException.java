package com.ciosmak.automotivepartner.statistic.support.exception;

public class IncorrectStatisticsDateException extends RuntimeException
{
    public Object[] properties;

    public IncorrectStatisticsDateException(String firstName, String lastName, Integer month, Integer year)
    {
        properties = new Object[]{firstName, lastName, month, year};
    }
}
