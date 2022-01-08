package com.example.frestraw.group;

import javax.persistence.*;

@Table(name = "GROUPS")
@Entity
public class Group {

    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private String ownerEmail;
    private String password;

    public Group() {}

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
