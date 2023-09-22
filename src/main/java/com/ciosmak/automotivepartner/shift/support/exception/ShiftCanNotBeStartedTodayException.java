package com.ciosmak.automotivepartner.shift.support.exception;

public class ShiftCanNotBeStartedTodayException extends RuntimeException
{
    public ShiftCanNotBeStartedTodayException(Long id)
    {
        super(String.format("Zmiana o %d id nie może zostać dziś rozpoczęta.", id));
    }
}
