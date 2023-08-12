package com.ciosmak.automotivepartner.user.support.exception;

public class UserAlreadyDriverException extends RuntimeException
{
    public UserAlreadyDriverException()
    {
        super("Użytkownik jest już kierowcą.");
    }
}
