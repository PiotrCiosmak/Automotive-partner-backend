package com.ciosmak.automotivepartner.shared.exception;

public class EmptyMileagePhotoException extends RuntimeException
{
    public EmptyMileagePhotoException()
    {
        super("Załącz zdjęcie przebiegu.");
    }
}
