package com.ciosmak.automotivepartner.car.support.exception;

public class CarAssignedToShiftException extends RuntimeException
{
    public CarAssignedToShiftException()
    {
        super("Auto jest przypisane do co najmiej jednej zmiany.");
    }
}
