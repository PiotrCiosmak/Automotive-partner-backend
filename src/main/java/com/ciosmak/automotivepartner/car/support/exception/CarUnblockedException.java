package com.ciosmak.automotivepartner.car.support.exception;

public class CarUnblockedException extends RuntimeException
{
    public CarUnblockedException(Long id)
    {
        super(String.format("Car with %d id is unblocked", id));
    }
}
