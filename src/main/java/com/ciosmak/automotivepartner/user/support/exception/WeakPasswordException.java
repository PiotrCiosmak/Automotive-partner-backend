package com.ciosmak.automotivepartner.user.support.exception;

public class WeakPasswordException extends RuntimeException
{
    public WeakPasswordException()
    {
        super("Wybierz mocniejsze hasło. Spróbuj kombinacji liter, numerów i znaków specjalnych.");
    }
}
