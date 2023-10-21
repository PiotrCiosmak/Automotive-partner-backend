package com.ciosmak.automotivepartner.token.support.exception;

public class NotExpiredChangePasswordTokenException extends RuntimeException
{
    public NotExpiredChangePasswordTokenException()
    {
        super("Link do zmiany hasła został już wysłany na ten adres email. Jest on nadal aktywny.");
    }
}
