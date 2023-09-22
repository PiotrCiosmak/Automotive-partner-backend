package com.ciosmak.automotivepartner.accident.support.exception;

public class DocumentPhotosNotFoundException extends RuntimeException
{
    public DocumentPhotosNotFoundException()
    {
        super(" Załącz minimum 1 zdjęcie dokumentów.");
    }
}
