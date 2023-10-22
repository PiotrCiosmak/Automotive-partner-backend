package com.ciosmak.automotivepartner.shift.support.exception;

import com.ciosmak.automotivepartner.shift.support.Type;

import java.time.LocalDate;

public class ShiftStartException extends RuntimeException
{
    public Object[] properties;

    public ShiftStartException(Long userId, LocalDate date, Type type)
    {
        properties = new Object[]{userId, date, type};
    }
}
