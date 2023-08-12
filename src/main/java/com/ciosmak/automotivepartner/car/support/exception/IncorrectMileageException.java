package com.ciosmak.automotivepartner.car.support.exception;

public class IncorrectMileageException extends RuntimeException
{
    public IncorrectMileageException()
    {
        super("Podaj poprawny przebieg auta.");
    }
}
