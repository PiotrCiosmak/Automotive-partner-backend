package com.ciosmak.automotivepartner.car.support.exception;

public class EmptyMileageException extends RuntimeException
{
    public EmptyMileageException()
    {
        super("Podaj przebieg auta.");
    }
}
