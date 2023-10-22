package com.ciosmak.automotivepartner.car.support.exception;

public class CarNotFoundException extends RuntimeException
{
    public Object[] properties;

    public CarNotFoundException(Long id)
    {
        properties = new Object[]{id};
    }
}
