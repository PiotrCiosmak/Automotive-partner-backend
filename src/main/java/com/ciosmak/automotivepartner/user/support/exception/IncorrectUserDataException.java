package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectUserDataException extends RuntimeException
{
    public IncorrectUserDataException()
    {
        super("Podane dane są nieprawidłowe");
    }
}
