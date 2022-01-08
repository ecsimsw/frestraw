package com.example.frestraw.card.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected Item() {}

    public Item(String name) {
        this.name = name;
    }

    public void update(Item other) {
        this.name = other.name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
