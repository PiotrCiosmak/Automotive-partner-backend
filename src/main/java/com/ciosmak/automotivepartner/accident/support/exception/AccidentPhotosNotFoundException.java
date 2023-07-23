package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentPhotosNotFoundException extends RuntimeException
{
    public AccidentPhotosNotFoundException()
    {
        super("Zdjęcią uszkodzeń nie zostały dodane");
    }
}
