package com.ciosmak.automotivepartner.user.support.exception;

public class WeakPasswordException extends RuntimeException
{
    public WeakPasswordException()
    {
        super("Wybierz silniejsze hasło. Spróbuj kombinacji liter, cyfr i znaków specjalnych.");
    }
}
