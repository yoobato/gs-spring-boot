package com.yoobato.sample.controller;

import com.yoobato.sample.entity.Item;
import com.yoobato.sample.exception.ItemNotFoundException;
import com.yoobato.sample.repositoy.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found")
        );
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setOwner(itemDto.getOwner());

        itemRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id, @RequestBody Item itemDto) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found")
        );
        item.setName(itemDto.getName());
        item.setOwner(itemDto.getOwner());

        itemRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found")
        );
        itemRepository.delete(item);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
