package com.example.frestraw.group;

import com.example.frestraw.card.Card;
import com.example.frestraw.group.CardGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardGroupRepository extends JpaRepository<CardGroup, Long> {
    List<CardGroup> findAllByCard(Card card);

    List<CardGroup> findAllByGroup(Group group);
    List<CardGroup> findAllByGroupId(Long groupId);
}
