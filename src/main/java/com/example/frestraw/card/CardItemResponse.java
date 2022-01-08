package com.example.frestraw.card;

public class CardItemResponse {

    private final Long id;
    private final Long cardId;
    private final String itemName;
    private final String value;

    public CardItemResponse(Long id, Long cardId, String itemName, String value) {
        this.id = id;
        this.cardId = cardId;
        this.itemName = itemName;
        this.value = value;
    }

    public static CardItemResponse of(CardItem cardItem) {
        return new CardItemResponse(
            cardItem.getId(),
            cardItem.getCardId(),
            cardItem.getItem().getName(),
            cardItem.getValue()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getCardId() {
        return cardId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getValue() {
        return value;
    }
}
