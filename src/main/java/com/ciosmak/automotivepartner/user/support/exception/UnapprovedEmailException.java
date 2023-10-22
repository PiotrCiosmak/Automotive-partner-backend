package com.ciosmak.automotivepartner.user.support.exception;

public class UnapprovedEmailException extends RuntimeException
{
    public Object[] properties;

    public UnapprovedEmailException(String email)
    {
        properties = new Object[]{email};
    }
}
