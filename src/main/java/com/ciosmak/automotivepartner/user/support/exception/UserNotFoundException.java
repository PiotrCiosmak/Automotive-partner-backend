package com.ciosmak.automotivepartner.user.support.exception;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(Long id)
    {
        super(String.format("Brak użytkownika o id %d.", id));
    }
}
