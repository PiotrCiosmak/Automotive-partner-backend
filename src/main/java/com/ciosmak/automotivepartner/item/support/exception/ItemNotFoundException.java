package com.ciosmak.automotivepartner.item.support.exception;

public class ItemNotFoundException extends RuntimeException
{
    public ItemNotFoundException(Long id)
    {
        super(String.format("Item with %d not found", id));
    }
}
