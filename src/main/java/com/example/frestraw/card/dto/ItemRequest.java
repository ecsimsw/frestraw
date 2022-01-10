package com.example.frestraw.card.dto;

import com.example.frestraw.card.domain.Item;

public class ItemRequest {

    private String name;

    public ItemRequest() {

    }

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
