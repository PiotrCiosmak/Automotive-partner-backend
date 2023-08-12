package com.ciosmak.automotivepartner.user.support.exception;

public class UserAlreadyBlockedException extends RuntimeException
{
    public UserAlreadyBlockedException()
    {
        super("Użytkownik zostało już wcześniej zablokowane.");
    }
}
