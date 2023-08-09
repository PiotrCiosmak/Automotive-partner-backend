package com.ciosmak.automotivepartner.user.support.exception;

public class UserBlockedException extends RuntimeException
{
    public UserBlockedException()
    {
        super("Twoje konto jest zablokowane, skontakuj się z administratorem.");
    }
}
