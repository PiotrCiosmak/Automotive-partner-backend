package com.ciosmak.automotivepartner.availability.support.exception;

import java.time.LocalDate;

public class IncorrectDateException extends RuntimeException
{
    public IncorrectDateException(String firstName, String lastName, LocalDate date)
    {
        super(String.format("Brak złożonej dyspozycyjności dla %s %s na dzień %s.", firstName, lastName, date));
    }
}
