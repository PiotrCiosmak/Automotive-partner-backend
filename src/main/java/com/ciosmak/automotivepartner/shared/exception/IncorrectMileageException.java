package com.ciosmak.automotivepartner.shared.exception;

public class IncorrectMileageException extends RuntimeException
{
    public IncorrectMileageException()
    {
        super("Podaj poprawny przebieg auta.");
    }
}
