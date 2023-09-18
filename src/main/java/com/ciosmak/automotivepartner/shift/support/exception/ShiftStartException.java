package com.ciosmak.automotivepartner.shift.support.exception;

import com.ciosmak.automotivepartner.shift.support.Type;

import java.time.LocalDate;

public class ShiftStartException extends RuntimeException
{
    public ShiftStartException(Long userId, LocalDate date, Type type)
    {
        super(String.format("Użytkownik o id %d nie został przydzielony do rozpoczęcia zmiany %s %s.", userId, date, type));
    }
}
