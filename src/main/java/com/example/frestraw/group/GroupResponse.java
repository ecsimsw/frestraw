package com.example.frestraw.group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupResponse {

    private final Long id;
    private final String name;
    private final String ownerEmail;

    public GroupResponse(Long id, String name, String ownerEmail) {
        this.id = id;
        this.name = name;
        this.ownerEmail = ownerEmail;
    }

    public static GroupResponse of(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getOwnerEmail()
        );
    }

    public static List<GroupResponse> listOf(List<Group> groups) {
        return groups.stream().map(GroupResponse::of).collect(Collectors.toList());
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
}
