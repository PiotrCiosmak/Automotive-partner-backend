package com.ciosmak.automotivepartner.shared.exception;

public class EmailTakenException extends RuntimeException
{
    public Object[] properties;

    public EmailTakenException(String email)
    {
        properties = new Object[]{email};
    }
}
