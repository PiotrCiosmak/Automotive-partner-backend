package com.ciosmak.automotivepartner.user.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException()
    {
        super("Podany email jest już zajęty");
    }
}
