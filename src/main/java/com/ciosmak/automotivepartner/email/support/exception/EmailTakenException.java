package com.ciosmak.automotivepartner.email.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException(String email)
    {
        super(String.format("Adres email: %s został już dodany. Podaj inny adres email.", email));
    }
}
