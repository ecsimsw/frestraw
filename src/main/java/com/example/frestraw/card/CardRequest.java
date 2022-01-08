package com.example.frestraw.card;

public class CardRequest {

    private String name;
    private String email;
    private String gender;
    private String profession;

    public CardRequest(String name, String email, String gender, String profession) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
    }

    public Card toEntity() {
        return new Card(name, email, gender, profession);
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
}
