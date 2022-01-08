package com.example.frestraw.group;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Transactional
    public GroupResponse create(GroupRequest request) {
        final Group group = request.toEntity();
        groupRepository.save(group);
        return GroupResponse.of(group);
    }

    @Transactional(readOnly = true)
    public List<GroupResponse> findAllGroups() {
        return groupRepository.findAll().stream()
            .map(GroupResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponse findById(Long id) {
        final Group group = groupRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return GroupResponse.of(group);
    }

    @Transactional
    public GroupResponse update(Long id, GroupRequest request) {
        final Group group = groupRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        group.update(request.toEntity());
        return GroupResponse.of(group);
    }
}
