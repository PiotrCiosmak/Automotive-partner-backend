package com.ciosmak.automotivepartner.car.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CarRepository
{
    protected final Map<Long, Car> map = new HashMap<>();
    protected long counter = 1;

    public Car save(Car entity)
    {
        setData(entity);
        return entity;
    }

    private Car setData(Car entity)
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

    public Optional<Car> findById(Long id)
    {
        return Optional.ofNullable(map.get(id));
    }

    public List<Car> findAll()
    {
        return new ArrayList<>(map.values());
    }

    public void deleteById(Long id)
    {
        map.remove(id);
    }
}
