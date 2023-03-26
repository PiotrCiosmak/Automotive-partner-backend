package com.ciosmak.automotivepartner.item.service;

import com.ciosmak.automotivepartner.item.api.request.ItemRequest;
import com.ciosmak.automotivepartner.item.api.response.ItemResponse;
import com.ciosmak.automotivepartner.item.domain.Item;
import com.ciosmak.automotivepartner.item.repository.ItemRepository;
import com.ciosmak.automotivepartner.item.support.ItemMapper;
import org.springframework.stereotype.Service;

@Service
public class ItemService
{
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper)
    {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    public ItemResponse create(ItemRequest itemRequest)
    {
        Item item = itemRepository.save(itemMapper.toItem(itemRequest));
        return itemMapper.toItemResponse(item);
    }
}
