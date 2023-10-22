package com.ciosmak.automotivepartner.user.support.exception;

public class UserNotFoundException extends RuntimeException
{
    public Object[] properties;

    public UserNotFoundException(Long id)
    {
        properties = new Object[]{id};
    }
}
