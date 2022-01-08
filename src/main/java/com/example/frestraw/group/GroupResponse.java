package com.example.frestraw.group;

public class GroupResponse {

    private final String name;

    public GroupResponse(String name) {
        this.name = name;
    }

    public static GroupResponse of(Group group) {
        return new GroupResponse("hihi");
    }

    public String getName() {
        return name;
    }
}
