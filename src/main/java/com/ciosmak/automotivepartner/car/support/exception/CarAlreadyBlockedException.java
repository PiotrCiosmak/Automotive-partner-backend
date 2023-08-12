package com.ciosmak.automotivepartner.car.support.exception;

public class CarAlreadyBlockedException extends RuntimeException
{
    public CarAlreadyBlockedException()
    {
        super("Auto zostało już wcześniej zablokowane.");
    }
}
