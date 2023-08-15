package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.email.support.exception.EmailTakenException;
import com.ciosmak.automotivepartner.email.support.exception.EmptyEmailException;
import com.ciosmak.automotivepartner.email.support.exception.IncorrectEmailException;

import java.util.function.Supplier;

public class EmailExceptionSupplier
{
    public static Supplier<EmailTakenException> emailTaken(String email)
    {
        return () -> new EmailTakenException(email);
    }

    public static Supplier<EmptyEmailException> emptyEmail()
    {
        return EmptyEmailException::new;
    }

    public static Supplier<IncorrectEmailException> incorrectEmail()
    {
        return IncorrectEmailException::new;
    }
}
