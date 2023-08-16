package com.ciosmak.automotivepartner.shared.exception;

public class EmptyEmailException extends RuntimeException
{
    public EmptyEmailException()
    {
        super("Podaj adres email.");
    }
}
