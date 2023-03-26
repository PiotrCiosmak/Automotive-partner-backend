package com.ciosmak.automotivepartner.item.api;

import com.ciosmak.automotivepartner.item.api.request.ItemRequest;
import com.ciosmak.automotivepartner.item.api.request.UpdateItemRequest;
import com.ciosmak.automotivepartner.item.api.response.ItemResponse;
import com.ciosmak.automotivepartner.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find item")
    public ResponseEntity<ItemResponse> find(@PathVariable Long id)
    {
        ItemResponse itemResponse = itemService.find(id);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @PutMapping
    @Operation(summary = "Update item")
    public ResponseEntity<ItemResponse> update(@RequestBody UpdateItemRequest updateItemRequest)
    {
        ItemResponse itemResponse = itemService.update(updateItemRequest);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @GetMapping
    @Operation(summary = "Find all items")
    public ResponseEntity<List<ItemResponse>> findAll()
    {
        List<ItemResponse> itemResponses = itemService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(itemResponses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        itemService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
