package com.example.frestraw.card.domain;

import java.util.List;

import com.example.frestraw.card.domain.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardItemRepository extends JpaRepository<CardItem, Long> {

    List<CardItem> findAllByCardId(Long cardId);

    void deleteAllByCardId(Long cardId);
}
