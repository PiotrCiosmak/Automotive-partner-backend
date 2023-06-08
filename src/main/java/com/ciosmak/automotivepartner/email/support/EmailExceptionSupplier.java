package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.email.support.exception.EmailTakenException;
import com.ciosmak.automotivepartner.email.support.exception.IncorrectEmailException;

import java.util.function.Supplier;

public class EmailExceptionSupplier
{
    public static Supplier<EmailTakenException> emailTakenException()
    {
        return () -> new EmailTakenException();
    }

    public static Supplier<IncorrectEmailException> incorrectEmail()
    {
        return () -> new IncorrectEmailException();
    }
}
