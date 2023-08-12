package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.support.exception.IncorrectDateException;

import java.util.function.Supplier;

public class StatisticsExceptionSupplier
{
    public static Supplier<IncorrectDateException> Incorrect()
    {
        return IncorrectDateException::new;
    }
}
