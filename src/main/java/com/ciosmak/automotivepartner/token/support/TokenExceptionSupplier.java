package com.ciosmak.automotivepartner.token.support;

import com.ciosmak.automotivepartner.token.support.exception.InvalidTokenException;

import java.util.function.Supplier;

public class TokenExceptionSupplier
{
    public static Supplier<InvalidTokenException> invalidToken()
    {
        return InvalidTokenException::new;
    }
}
