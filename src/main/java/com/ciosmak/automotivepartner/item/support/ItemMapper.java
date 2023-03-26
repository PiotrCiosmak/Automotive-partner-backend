package com.ciosmak.automotivepartner.item.support;

import com.ciosmak.automotivepartner.item.api.request.ItemRequest;
import com.ciosmak.automotivepartner.item.api.request.UpdateItemRequest;
import com.ciosmak.automotivepartner.item.api.response.ItemResponse;
import com.ciosmak.automotivepartner.item.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper
{
    public Item toItem(ItemRequest itemRequest)
    {
        return new Item(itemRequest.getName());
    }

    public Item toItem(Item item, UpdateItemRequest itemRequest)
    {
        item.setName(itemRequest.getName());
        return item;
    }

    public Item toItem(Item item, ItemRequest itemRequest)
    {
        item.setName(itemRequest.getName());
        return item;
    }

    public ItemResponse toItemResponse(Item item)
    {
        return new ItemResponse(item.getId(), item.getName());
    }
}
