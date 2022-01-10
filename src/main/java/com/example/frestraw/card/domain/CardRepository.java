package com.example.frestraw.card.domain;

import java.util.Optional;

import com.example.frestraw.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByEmail(String email);
}
