package com.ciosmak.automotivepartner.item.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ItemRequest
{
    private final String name;

    @JsonCreator
    public ItemRequest(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
