package com.ciosmak.automotivepartner.user.support.exception;

public class EmailIsNotInDatabase extends RuntimeException
{
    public EmailIsNotInDatabase(String email)
    {
        super(String.format("Email: %s is not in database", email));
    }
}
