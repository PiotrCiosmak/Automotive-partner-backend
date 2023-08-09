package com.ciosmak.automotivepartner.user.support.exception;

public class UserNotAdminException extends RuntimeException
{
    public UserNotAdminException()
    {
        super("Użytkownik nie jest administratorem.");
    }
}
