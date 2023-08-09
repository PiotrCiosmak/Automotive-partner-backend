package com.ciosmak.automotivepartner.user.support.exception;

public class UserAlreadyAdminException extends RuntimeException
{
    public UserAlreadyAdminException()
    {
        super("Użytkownik jest już administratorem.");
    }
}
