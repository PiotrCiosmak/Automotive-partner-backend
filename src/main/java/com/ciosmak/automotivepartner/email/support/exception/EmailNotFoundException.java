package com.ciosmak.automotivepartner.email.support.exception;

public class EmailNotFoundException extends RuntimeException
{
    public EmailNotFoundException()
    {
        super("Podany email nie znajduje się w bazie");
    }
}
