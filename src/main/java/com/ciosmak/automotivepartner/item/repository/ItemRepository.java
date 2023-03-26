package com.ciosmak.automotivepartner.item.repository;

import com.ciosmak.automotivepartner.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
}
