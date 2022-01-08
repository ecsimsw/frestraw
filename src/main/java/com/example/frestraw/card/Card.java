package com.example.frestraw.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Card {

//    Request
//    이름
//    성별
//    이메일
//    직업
//
//    Optional - field
//    한줄소개
//    전화번호
//    나이 & 생년월일
//    취미
//    MBTI
//    지역
//    학교
//    소속
//    웹사이트
//
//    Optional - select
//    좋아하는 음식

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String profession;
//    private List<CardGroup> cardGroups;

    protected Card() {}

    public void update(Card other) {
        this.name = other.name;
        this.email = other.email;
        this.gender = other.gender;
    }

    public Long getId() {
        return id;
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
}
