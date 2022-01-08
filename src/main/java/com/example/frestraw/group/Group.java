package com.example.frestraw.group;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String ownerEmail;
    private String password;

    protected Group() {}

    public void update(Group other) {
        this.name = other.name;
        this.password = other.password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getPassword() {
        return password;
    }
}
