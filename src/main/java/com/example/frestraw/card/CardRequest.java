package com.example.frestraw.card;

import java.util.List;
import java.util.stream.Collectors;

public class CardRequest {

    private String name;
    private String email;
    private String gender;
    private String profession;
    private List<CardItemRequest> cardItems;

    public CardRequest(String name, String email, String gender, String profession,
                       List<CardItemRequest> cardItems) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.cardItems = cardItems;
    }

    public Card toCard() {
        return new Card(name, email, gender, profession);
    }

    public List<CardItem> toCardItems(Card card) {
        return cardItems.stream()
            .map(cardItemRequest -> cardItemRequest.of(card.getId()))
            .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getProfession() {
        return profession;
    }

    public List<CardItemRequest> getCardItems() {
        return cardItems;
    }
}
