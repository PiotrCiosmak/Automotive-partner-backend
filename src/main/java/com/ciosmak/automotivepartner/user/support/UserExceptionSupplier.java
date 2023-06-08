package com.ciosmak.automotivepartner.user.support;

import com.ciosmak.automotivepartner.user.support.exception.EmailAlreadyExists;
import com.ciosmak.automotivepartner.user.support.exception.IncorrectUserData;
import com.ciosmak.automotivepartner.user.support.exception.UserNotFoundException;

import java.util.function.Supplier;

public class UserExceptionSupplier
{
    public static Supplier<UserNotFoundException> userNotFound(Long id)
    {
        return () -> new UserNotFoundException(id);
    }

    public static Supplier<EmailAlreadyExists> emailAlreadyExists(String email)
    {
        return () -> new EmailAlreadyExists(email);
    }

    public static Supplier<IncorrectUserData> incorrectData()
    {
        return () -> new IncorrectUserData();
    }
}
