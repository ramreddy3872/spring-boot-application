package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemService {

    private final ConcurrentHashMap<Long, Item> itemMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public ItemService() {
        // Seed with sample data
        createItem(new Item(null, "Laptop", "High-performance laptop", 1299.99));
        createItem(new Item(null, "Wireless Mouse", "Ergonomic Bluetooth mouse", 29.99));
        createItem(new Item(null, "Mechanical Keyboard", "RGB backlit keyboard", 89.99));
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }

    public Item getItemById(Long id) {
        Item item = itemMap.get(id);
        if (item == null) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        return item;
    }

    public Item createItem(Item item) {
        Long newId = idGenerator.incrementAndGet();
        item.setId(newId);
        itemMap.put(newId, item);
        return item;
    }

    public Item updateItem(Long id, Item updatedItem) {
        if (!itemMap.containsKey(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        updatedItem.setId(id);
        itemMap.put(id, updatedItem);
        return updatedItem;
    }

    public void deleteItem(Long id) {
        if (!itemMap.containsKey(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        itemMap.remove(id);
    }
}
