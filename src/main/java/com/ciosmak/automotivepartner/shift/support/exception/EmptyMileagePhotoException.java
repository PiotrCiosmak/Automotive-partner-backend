package com.ciosmak.automotivepartner.shift.support.exception;

public class EmptyMileagePhotoException extends RuntimeException
{
    public EmptyMileagePhotoException()
    {
        super("Załącz zdjęcie przebiegu.");
    }
}
