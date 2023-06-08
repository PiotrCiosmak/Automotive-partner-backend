package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectUserData extends RuntimeException
{
    public IncorrectUserData()
    {
        super("Podane dane są nie poprawne");
    }
}
