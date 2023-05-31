package com.ciosmak.automotivepartner.car.repository;

import com.ciosmak.automotivepartner.car.domain.Car;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CarRepository
{
    protected final Map<Long, Car> map = new HashMap<>();
    protected long counter = 1;

    public Car save(Car entity)
    {
        setId(entity);
        return entity;
    }

    private Car setId(Car entity)
    {
        entity.setId(counter);
        map.put(counter,entity);
        counter++;
        return entity;
    }
}
