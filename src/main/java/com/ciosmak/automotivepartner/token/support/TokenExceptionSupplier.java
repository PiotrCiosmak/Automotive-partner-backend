package com.ciosmak.automotivepartner.token.support;

import com.ciosmak.automotivepartner.shared.exception.InvalidTokenException;
import com.ciosmak.automotivepartner.token.support.exception.ExpiredChangePasswordTokenException;
import com.ciosmak.automotivepartner.token.support.exception.NotExpiredChangePasswordTokenException;

import java.util.function.Supplier;

public class TokenExceptionSupplier
{
    public static Supplier<ExpiredChangePasswordTokenException> expiredChangePasswordToken()
    {
        return ExpiredChangePasswordTokenException::new;
    }

    public static Supplier<InvalidTokenException> invalidToken()
    {
        return InvalidTokenException::new;
    }

    public static Supplier<NotExpiredChangePasswordTokenException> notExpiredChangePasswordToken()
    {
        return NotExpiredChangePasswordTokenException::new;
    }
}
