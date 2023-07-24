package com.ciosmak.automotivepartner.statistic.support.exception;

public class UserStatisticsNotExistsException extends RuntimeException
{
    public UserStatisticsNotExistsException(Long userId)
    {
        super(String.format("Kierowca o id %d nie posiada jeszcze statystyk", userId));
    }
}
