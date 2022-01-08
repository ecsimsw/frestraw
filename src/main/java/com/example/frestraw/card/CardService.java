package com.example.frestraw.card;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardItemRepository cardItemRepository;

    public CardService(CardRepository cardRepository, CardItemRepository cardItemRepository) {
        this.cardRepository = cardRepository;
        this.cardItemRepository = cardItemRepository;
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        final Card card = request.toCard();
        cardRepository.save(card);
        final List<CardItem> cardItems = request.toCardItems(card);
        cardItemRepository.saveAll(cardItems);
        return CardResponse.of(card, cardItems);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAllCards() {
//        return cardRepository.findAll().stream()
//            .map(CardResponse::of)
//            .collect(Collectors.toList());
        return null;
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        return CardResponse.of(card, cardItems);
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        final List<CardItemRequest> cardItemUpdateRequests = request.getCardItems();
        card.update(request.toCard());
        for (CardItem cardItem : cardItems) {
            for (CardItemRequest updateRequest : cardItemUpdateRequests) {
                if (cardItem.getItemId().equals(updateRequest.getItemId())) {
                    cardItem.update(updateRequest.getValue());
                }
            }
        }
        return CardResponse.of(card, cardItems);
    }
}
