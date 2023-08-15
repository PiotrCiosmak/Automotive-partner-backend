package com.ciosmak.automotivepartner.user.support.exception;

public class UnapprovedEmailException extends RuntimeException
{
    public UnapprovedEmailException(String email)
    {
        super(String.format("Adres email: %s nie został zatwierdzony przez administratora. Podaj taki adres który został zatwierdzony.", email));
    }
}
