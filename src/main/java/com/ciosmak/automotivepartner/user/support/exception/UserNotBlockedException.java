package com.ciosmak.automotivepartner.user.support.exception;

public class UserNotBlockedException extends RuntimeException
{
    public UserNotBlockedException()
    {
        super("Użytkownik nie jest zablokowany.");
    }
}
