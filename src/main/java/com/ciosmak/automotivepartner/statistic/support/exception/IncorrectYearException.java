package com.ciosmak.automotivepartner.statistic.support.exception;

public class IncorrectYearException extends RuntimeException
{
    public Object[] properties;

    public IncorrectYearException(Integer year)
    {
        properties = new Object[]{year};
    }
}
