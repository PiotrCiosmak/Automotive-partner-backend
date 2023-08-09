package com.ciosmak.automotivepartner.email.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException()
    {
        super("Ten adres email został już dodany. Podaj inny adres email.");
    }
}
