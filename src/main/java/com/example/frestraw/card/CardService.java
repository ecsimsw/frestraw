package com.example.frestraw.card;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Transactional
    public CardResponse create(CardRequest request) {
        final Card card = request.toEntity();
        cardRepository.save(card);
        return CardResponse.of(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAllCards() {
        return cardRepository.findAll().stream()
            .map(CardResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return CardResponse.of(card);
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        card.update(request.toEntity());
        return CardResponse.of(card);
    }
}
