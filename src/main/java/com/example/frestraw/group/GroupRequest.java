package com.example.frestraw.group;

public class GroupRequest {

    private final String name;
    private final String password;

    public GroupRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Group toEntity() {
        return new Group(name, password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
