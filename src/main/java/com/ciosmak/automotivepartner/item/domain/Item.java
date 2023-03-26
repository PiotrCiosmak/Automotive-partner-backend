package com.ciosmak.automotivepartner.item.domain;

public class Item
{
    private Long id;
    private String name;

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

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
}
