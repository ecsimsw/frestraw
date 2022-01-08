package com.example.frestraw.group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupResponse {

    private final String name;

    public GroupResponse(String name) {
        this.name = name;
    }

    public static GroupResponse of(Group group) {
        return new GroupResponse("hihi");
    }

    public static List<GroupResponse> listOf(List<Group> groups) {
        return groups.stream().map(it->of(it)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }
}
