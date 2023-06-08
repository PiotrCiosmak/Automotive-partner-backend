package com.ciosmak.automotivepartner.user.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException(String email)
    {
        super(String.format("Email: %s is already taken", email));
    }
}
