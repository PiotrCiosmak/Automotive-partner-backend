package com.ciosmak.automotivepartner.user.support.exception;

public class EmailIsNotInDatabaseException extends RuntimeException
{
    public EmailIsNotInDatabaseException(String email)
    {
        super(String.format("Email: %s is not in database", email));
    }
}
