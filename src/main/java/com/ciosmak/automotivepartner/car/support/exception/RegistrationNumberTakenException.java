package com.ciosmak.automotivepartner.car.support.exception;

public class RegistrationNumberTakenException extends RuntimeException
{
    public Object[] properties;

    public RegistrationNumberTakenException(String registrationNumber)
    {
        properties = new Object[]{registrationNumber};
    }
}
