package com.ciosmak.automotivepartner.user.support.exception;

public class EmailTakenException extends RuntimeException
{
    public EmailTakenException(String email)
    {
        super(String.format("Adres email: %s został już użyty. Podaj inny adres email.", email));
    }
}
