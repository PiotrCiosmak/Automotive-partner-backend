package com.ciosmak.automotivepartner.shared.exception;

public class EmptyMileageException extends RuntimeException
{
    public EmptyMileageException()
    {
        super("Podaj przebieg auta.");
    }
}
