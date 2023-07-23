package com.ciosmak.automotivepartner.accident.support.exception;

public class MileagePhotoNotFoundException extends RuntimeException
{
    public MileagePhotoNotFoundException()
    {
        super("Zdjęcie przebiegu nie zostało dodane");
    }
}
