package com.ciosmak.automotivepartner.token.support;

import com.ciosmak.automotivepartner.token.support.exception.InvalidTokenException;
import com.ciosmak.automotivepartner.token.support.exception.NotExpiredTokenException;

import java.util.function.Supplier;

public class TokenExceptionSupplier
{
    public static Supplier<InvalidTokenException> invalidToken()
    {
        return InvalidTokenException::new;
    }

    public static Supplier<NotExpiredTokenException> notExpiredToken()
    {
        return NotExpiredTokenException::new;
    }
}
