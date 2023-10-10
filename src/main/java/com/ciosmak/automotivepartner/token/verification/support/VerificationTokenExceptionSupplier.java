package com.ciosmak.automotivepartner.token.verification.support;

import com.ciosmak.automotivepartner.token.verification.support.exception.InvalidVerificationTokenException;

import java.util.function.Supplier;

public class VerificationTokenExceptionSupplier
{
    public static Supplier<InvalidVerificationTokenException> invalidVerificationToken()
    {
        return InvalidVerificationTokenException::new;
    }
}
