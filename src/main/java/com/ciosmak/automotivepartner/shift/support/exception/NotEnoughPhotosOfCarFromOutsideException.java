package com.ciosmak.automotivepartner.shift.support.exception;

public class NotEnoughPhotosOfCarFromOutsideException extends RuntimeException
{
    public NotEnoughPhotosOfCarFromOutsideException()
    {
        super("Załącz minimum 8 zdjęć auta z zewnątrz.");
    }
}
