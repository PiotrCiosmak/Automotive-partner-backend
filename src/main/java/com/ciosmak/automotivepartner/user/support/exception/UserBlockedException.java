package com.ciosmak.automotivepartner.user.support.exception;

public class UserBlockedException extends RuntimeException
{
    public UserBlockedException(Long id)
    {
        super(String.format("User with %d id is blocked", id));
    }
}
