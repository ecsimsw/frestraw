package com.example.frestraw.card;

public class CardItemRequest {

    private final Long itemId;
    private final String value;

    public CardItemRequest(Long itemId, String value) {
        this.itemId = itemId;
        this.value = value;
    }

    public CardItem of(Long cardId) {
        return new CardItem(cardId, this.itemId, this.value);
    }

    public Long getItemId() {
        return itemId;
    }

    public String getValue() {
        return value;
    }
}
