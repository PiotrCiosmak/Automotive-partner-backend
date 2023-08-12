package com.ciosmak.automotivepartner.user.support.exception;

public class UnapprovedEmailException extends RuntimeException
{
    public UnapprovedEmailException()
    {
        super("Podaj adres który został zatwierdzony przez administratora.");
    }
}
