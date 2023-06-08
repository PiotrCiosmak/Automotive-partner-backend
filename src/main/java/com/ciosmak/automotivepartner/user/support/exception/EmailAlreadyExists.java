package com.ciosmak.automotivepartner.user.support.exception;

public class EmailAlreadyExists extends RuntimeException
{
    public EmailAlreadyExists(String email)
    {
        super(String.format("Email: %s is already taken", email));
    }
}
