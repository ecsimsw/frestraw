package com.example.frestraw.card.item;

public class ItemRequest {

    private final String name;

    public ItemRequest(String name) {
        this.name = name;
    }

    public Item toItem() {
        return new Item(name);
    }

    public String getName() {
        return name;
    }
}
