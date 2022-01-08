package com.example.frestraw.card;

import java.util.List;
import java.util.stream.Collectors;

public class CardSimpleResponse {

    private Long id;
    private String name;
    private String gender;

    public CardSimpleResponse(Long id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public static CardSimpleResponse of(Card card) {
        return new CardSimpleResponse(card.getId(), card.getName(), card.getGender());
    }

    public static List<CardSimpleResponse> listOf(List<Card> cardsInGroup) {
        return cardsInGroup.stream().map(it -> of(it)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
}
