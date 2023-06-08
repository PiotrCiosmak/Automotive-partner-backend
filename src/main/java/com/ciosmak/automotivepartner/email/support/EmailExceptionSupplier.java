package com.ciosmak.automotivepartner.email.support;

import com.ciosmak.automotivepartner.email.support.exception.EmailTakenException;
import com.ciosmak.automotivepartner.email.support.exception.InValidEmailException;

import java.util.function.Supplier;

public class EmailExceptionSupplier
{
    public static Supplier<EmailTakenException> emailTaken()
    {
        return EmailTakenException::new;
    }

    public static Supplier<InValidEmailException> inValidEmail()
    {
        return InValidEmailException::new;
    }
}
