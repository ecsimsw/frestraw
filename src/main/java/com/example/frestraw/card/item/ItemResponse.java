package com.example.frestraw.card.item;

public class ItemResponse {

    private final Long id;
    private final String name;

    public ItemResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
