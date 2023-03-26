package com.ciosmak.automotivepartner.item.support;

import com.ciosmak.automotivepartner.item.api.request.ItemRequest;
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

    public ItemResponse toItemResponse(Item item)
    {
        return new ItemResponse(item.getId(), item.getName());
    }
}
