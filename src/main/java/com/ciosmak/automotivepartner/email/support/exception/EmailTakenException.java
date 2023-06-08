package com.ciosmak.automotivepartner.email.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException()
    {
        super("Podany email jest już przypisany do istniejącego konta");
    }
}
