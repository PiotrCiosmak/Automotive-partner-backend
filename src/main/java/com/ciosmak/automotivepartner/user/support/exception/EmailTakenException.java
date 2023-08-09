package com.ciosmak.automotivepartner.user.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException()
    {
        super("Ten adres email został już użyty. Podaj inny adres email.");
    }
}
