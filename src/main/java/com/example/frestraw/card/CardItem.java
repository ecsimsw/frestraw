package com.example.frestraw.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardId;
    private Long itemId;
    private String value;

    protected CardItem() {}

    public CardItem(Long cardId, Long itemId, String value) {
        this.cardId = cardId;
        this.itemId = itemId;
        this.value = value;
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

    public void update(String value) {
        this.value = value;
    }
}
