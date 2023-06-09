package com.ciosmak.automotivepartner.user.support.exception;

public class EmailNotInDatabaseException extends RuntimeException
{
    public EmailNotInDatabaseException()
    {
        super("Podany email nie znajduje się w bazie");
    }
}
