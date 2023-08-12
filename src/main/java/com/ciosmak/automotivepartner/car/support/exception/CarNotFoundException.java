package com.ciosmak.automotivepartner.car.support.exception;

public class CarNotFoundException extends RuntimeException
{
    public CarNotFoundException(Long id)
    {
        super(String.format("Brak auta od id %d", id));
    }
}
