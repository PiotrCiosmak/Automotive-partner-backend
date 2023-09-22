package com.ciosmak.automotivepartner.accident.support.exception;

public class AccidentPhotosNotFoundException extends RuntimeException
{
    public AccidentPhotosNotFoundException()
    {
        super("Załącz minimum 4 zdjęcia pokazujące uszkodzenia.");
    }
}
