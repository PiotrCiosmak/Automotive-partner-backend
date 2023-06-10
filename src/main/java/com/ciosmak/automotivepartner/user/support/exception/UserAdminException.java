package com.ciosmak.automotivepartner.user.support.exception;

public class UserAdminException extends RuntimeException
{
    public UserAdminException(Long id)
    {
        super(String.format("User with %d id is admin", id));
    }
}
