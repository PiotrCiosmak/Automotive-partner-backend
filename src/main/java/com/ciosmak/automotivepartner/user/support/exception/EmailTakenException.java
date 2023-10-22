package com.ciosmak.automotivepartner.user.support.exception;

public class EmailTakenException extends RuntimeException
{
    public Object[] properties;

    public EmailTakenException(String email)
    {
        properties = new Object[]{email};
    }
}
