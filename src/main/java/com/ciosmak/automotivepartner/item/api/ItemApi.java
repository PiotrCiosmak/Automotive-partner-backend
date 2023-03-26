package com.ciosmak.automotivepartner.item.api;

import com.ciosmak.automotivepartner.item.api.request.ItemRequest;
import com.ciosmak.automotivepartner.item.api.response.ItemResponse;
import com.ciosmak.automotivepartner.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Items")
@RequestMapping("/api/items")
public class ItemApi
{
    private final ItemService itemService;

    ItemApi(ItemService itemService)
    {
        this.itemService = itemService;
    }

    @PostMapping
    @Operation(summary = "Create item")
    public ResponseEntity<ItemResponse> create(@RequestBody ItemRequest itemRequest)
    {
        ItemResponse itemResponse = itemService.create(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find item")
    public ResponseEntity<ItemResponse> find(@PathVariable Long id)
    {
        ItemResponse itemResponse = itemService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }
}
