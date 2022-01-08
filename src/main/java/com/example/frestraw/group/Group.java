package com.example.frestraw.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Group {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String password;


    public void update(Group other) {
        this.name = other.name;
        this.password = other.password;
    }
}
