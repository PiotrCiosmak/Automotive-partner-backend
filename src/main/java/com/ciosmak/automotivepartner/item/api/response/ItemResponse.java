package com.ciosmak.automotivepartner.item.api.response;

public class ItemResponse
{
    private final Long id;
    private final String name;

    public ItemResponse(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
