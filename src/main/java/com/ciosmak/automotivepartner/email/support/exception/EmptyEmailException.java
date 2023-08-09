package com.ciosmak.automotivepartner.email.support.exception;

public class EmptyEmailException extends RuntimeException
{
    public EmptyEmailException()
    {
        super("Podaj adres email.");
    }
}
