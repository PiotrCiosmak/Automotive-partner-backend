package com.ciosmak.automotivepartner.item.support;

import com.ciosmak.automotivepartner.item.support.exception.ItemNotFoundException;

import java.util.function.Supplier;

public class ItemExceptionSupplier
{
    public static Supplier<ItemNotFoundException> itemNotFound(Long id)
    {
        return () -> new ItemNotFoundException(id);
    }
}
