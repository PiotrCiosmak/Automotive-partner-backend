package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectDateException;
import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectYearException;

import java.util.function.Supplier;

public class StatisticsExceptionSupplier
{
    public static Supplier<IncorrectDateException> incorrectDate(String firstName, String lastName, Integer month, Integer year)
    {
        return () -> new IncorrectDateException(firstName, lastName, month, year);
    }

    public static Supplier<IncorrectYearException> incorrectYear(Integer year)
    {
        return () -> new IncorrectYearException(year);
    }
}
