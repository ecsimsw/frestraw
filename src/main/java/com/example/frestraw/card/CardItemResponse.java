package com.example.frestraw.card;

public class CardItemResponse {

    private final Long id;
    private final Long cardId;
    private final Long itemId;
    private final String value;

    public CardItemResponse(Long id, Long cardId, Long itemId, String value) {
        this.id = id;
        this.cardId = cardId;
        this.itemId = itemId;
        this.value = value;
    }

    public static CardItemResponse of(CardItem cardItem) {
        return new CardItemResponse(
            cardItem.getId(),
            cardItem.getCardId(),
            cardItem.getItemId(),
            cardItem.getValue()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getCardId() {
        return cardId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getValue() {
        return value;
    }
}
