package com.ciosmak.automotivepartner.user.support.exception;

public class UserUnblockedException extends RuntimeException
{
    public UserUnblockedException(Long id)
    {
        super(String.format("User with %d id is unblocked", id));
    }
}
