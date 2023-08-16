package com.ciosmak.automotivepartner.shift.support.exception;

public class EmptyInvoicePhotoException extends RuntimeException
{
    public EmptyInvoicePhotoException()
    {
        super("Załącz zdjęcie faktury.");
    }
}
