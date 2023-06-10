package com.ciosmak.automotivepartner.car.support.exception;

public class CarBlockedException extends RuntimeException
{
    public CarBlockedException(Long id)
    {
        super(String.format("Car with %d id is blocked", id));
    }
}
