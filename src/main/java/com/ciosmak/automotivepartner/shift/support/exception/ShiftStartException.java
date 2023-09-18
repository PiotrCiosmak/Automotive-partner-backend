package com.ciosmak.automotivepartner.shift.support.exception;

import com.ciosmak.automotivepartner.availability.support.Type;

import java.time.LocalDate;

public class ShiftStartException extends RuntimeException
{
    public ShiftStartException(Long id, LocalDate date, Type type, Long carId)
    {
        super(String.format("Użytkownik o id %d nie został przydzielony do rozpoczęcia zmiany %s %s w aucie o id %d.", id, date, type, carId));
    }
}
