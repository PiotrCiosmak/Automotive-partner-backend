package com.ciosmak.automotivepartner.accident.support.exception;

public class IncorrectMileageException extends RuntimeException
{
    public IncorrectMileageException()
    {
        super("Wprowadzony przebieg jest błędny");
    }
}
