package com.ciosmak.automotivepartner.token.verification.support.exception;

public class InvalidVerificationTokenException extends RuntimeException
{
    public InvalidVerificationTokenException()
    {
        super("Token weryfikacyjny nie jest poprawny.");
    }
}
