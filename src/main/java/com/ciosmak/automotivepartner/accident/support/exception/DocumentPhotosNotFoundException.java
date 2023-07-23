package com.ciosmak.automotivepartner.accident.support.exception;

public class DocumentPhotosNotFoundException extends RuntimeException
{
    public DocumentPhotosNotFoundException()
    {
        super("Zdjęcia dokumentów nie dostały dodane");
    }
}
