package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectStatisticsDateException;
import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectYearException;

import java.util.function.Supplier;

public class StatisticsExceptionSupplier
{
    public static Supplier<IncorrectStatisticsDateException> incorrectStatisticsDate(String firstName, String lastName, Integer month, Integer year)
    {
        return () -> new IncorrectStatisticsDateException(firstName, lastName, month, year);
    }

    public static Supplier<IncorrectYearException> incorrectYear(Integer year)
    {
        return () -> new IncorrectYearException(year);
    }
}
