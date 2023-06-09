package com.ciosmak.automotivepartner.user.support.exception;

public class UserNotAdminException extends RuntimeException
{
    public UserNotAdminException(Long id)
    {
        super(String.format("User with %d id is already not admin", id));
    }
}
