package com.ciosmak.automotivepartner.car.support.exception;

public class IncorrectCarDetailsException extends RuntimeException
{
    public IncorrectCarDetailsException()
    {
        super("Podane dane są nieprawidłowe");
    }
}
