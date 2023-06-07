package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.user.support.exception.UserNotFoundException;

import java.util.function.Supplier;

public class UserExceptionSupplier
{
    public static Supplier<UserNotFoundException> userNotFound(Long id)
    {
        return () -> new UserNotFoundException(id);
    }
}
