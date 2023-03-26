package com.ciosmak.automotivepartner.item.repository;

import com.ciosmak.automotivepartner.item.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository
{
    protected final Map<Long, Item> map = new HashMap<>();
    protected long counter = 1;

    public Item save(Item entity)
    {
        setData(entity);
        return entity;
    }

    private Item setData(Item entity)
    {
        if (entity.getId() != null)
        {
            map.put(entity.getId(), entity);
        }
        else
        {
            entity.setId(counter);
            map.put(counter, entity);
            counter++;
        }
        return entity;
    }

    public Optional<Item> findById(Long id)
    {
        return Optional.ofNullable(map.get(id));
    }

    public List<Item> findAll()
    {
        return new ArrayList<>(map.values());
    }

    public void deleteById(Long id)
    {
        map.remove(id);
    }
}
