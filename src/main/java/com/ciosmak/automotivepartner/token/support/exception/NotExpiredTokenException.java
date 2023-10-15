package com.ciosmak.automotivepartner.token.support.exception;

public class NotExpiredTokenException extends RuntimeException
{
    public NotExpiredTokenException()
    {
        super("Link do zmiany hasła został już wysłany na ten adres email. Jest on nadal aktywny.");
    }
}
