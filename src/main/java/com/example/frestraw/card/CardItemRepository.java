package com.example.frestraw.card;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardItemRepository extends JpaRepository<CardItem, Long> {

    List<CardItem> findAllByCardId(Long cardId);
}
