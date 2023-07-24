package com.ciosmak.automotivepartner.statistic.support;

import com.ciosmak.automotivepartner.statistic.support.exception.UserStatisticsNotExistsException;

import java.util.function.Supplier;

public class StatisticsExceptionSupplier
{
    public static Supplier<UserStatisticsNotExistsException> userStatisticsNotExists(Long userId)
    {
        return () -> new UserStatisticsNotExistsException(userId);
    }
}
