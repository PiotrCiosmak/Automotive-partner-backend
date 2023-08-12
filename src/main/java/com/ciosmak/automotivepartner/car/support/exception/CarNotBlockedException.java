package com.ciosmak.automotivepartner.car.support.exception;

public class CarNotBlockedException extends RuntimeException
{
    public CarNotBlockedException()
    {
        super("Auto nie jest zablokowane.");
    }
}
