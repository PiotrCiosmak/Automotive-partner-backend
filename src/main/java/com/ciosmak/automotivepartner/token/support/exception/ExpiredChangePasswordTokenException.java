package com.ciosmak.automotivepartner.token.support.exception;

public class ExpiredChangePasswordTokenException extends RuntimeException
{
    public ExpiredChangePasswordTokenException()
    {
        super("Link do zmiany hasła już wygasł. Złóż prośbę o zmianę hasła ponownie.");
    }
}
