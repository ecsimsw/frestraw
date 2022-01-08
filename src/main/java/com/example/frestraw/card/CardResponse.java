package com.example.frestraw.card;

import java.util.List;
import java.util.stream.Collectors;

public class CardResponse {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private String profession;
    private List<CardItemResponse> cardItems;

    public CardResponse(Long id, String name, String email, String gender, String profession, List<CardItemResponse> cardItems) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.cardItems = cardItems;
    }

    public static CardResponse of(Card card, List<CardItem> cardItems) {
        final List<CardItemResponse> cardItemResponses = cardItems.stream()
            .map(CardItemResponse::of)
            .collect(Collectors.toList());
        return new CardResponse(
            card.getId(),
            card.getName(),
            card.getEmail(),
            card.getGender(),
            card.getProfession(),
            cardItemResponses
        );
    }

    public Long getId() {
        return id;
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

    public String getName() {
        return name;
    }
}
