package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.email.support.exception.EmailNotFoundException;
import com.ciosmak.automotivepartner.email.support.exception.EmailTakenException;
import com.ciosmak.automotivepartner.email.support.exception.InCorrectEmailException;

import java.util.function.Supplier;

public class EmailExceptionSupplier
{
    public static Supplier<EmailTakenException> emailTaken()
    {
        return EmailTakenException::new;
    }

    public static Supplier<InCorrectEmailException> inCorrectEmail()
    {
        return InCorrectEmailException::new;
    }

    public static Supplier<EmailNotFoundException> emailNotFound()
    {
        return EmailNotFoundException::new;
    }
}
