package com.example.frestraw.card;

public class CardResponse {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private String profession;

    public CardResponse(Long id, String name, String email, String gender, String profession) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
    }

    public static CardResponse of(Card card) {
        return new CardResponse(card.getId(), card.getName(), card.getEmail(), card.getGender(), card.getProfession());
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
