package com.example.frestraw.group.domain;

import com.example.frestraw.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
    List<CardGroup> findAllByCard(Card card);

    List<CardGroup> findAllByGroup(Group group);
    List<CardGroup> findAllByGroupId(Long groupId);
}
