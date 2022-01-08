package com.example.frestraw.card;

public class CardResponse {

    private final String name;

    public CardResponse(String name) {
        this.name = name;
    }

    public static CardResponse of(Card card) {
        return null;
    }

    public String getName() {
        return name;
    }
}
