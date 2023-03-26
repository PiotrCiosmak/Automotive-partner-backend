package com.ciosmak.automotivepartner.item.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UpdateItemRequest extends ItemRequest
{
    private final Long id;

    @JsonCreator
    public UpdateItemRequest(String name, Long id)
    {
        super(name);
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
}
