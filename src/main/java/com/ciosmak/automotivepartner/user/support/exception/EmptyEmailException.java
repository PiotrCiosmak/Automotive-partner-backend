package com.ciosmak.automotivepartner.user.support.exception;

public class EmptyEmailException extends RuntimeException
{
    public EmptyEmailException()
    {
        super("Podaj adres email.");
    }
}
