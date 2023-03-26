package com.ciosmak.automotivepartner.item.domain;

public class Item
{
    private Long id;
    private final String name;

    public Item(String name)
    {
        this.name = name;
    }

    public void setId(Long id)
    {
        this.id = id;
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
