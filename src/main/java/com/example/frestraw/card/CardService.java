package com.example.frestraw.card;

import java.util.List;
import java.util.stream.Collectors;

import com.example.frestraw.card.item.Item;
import com.example.frestraw.card.item.ItemRepository;
import com.example.frestraw.card.item.ItemRequest;
import com.example.frestraw.group.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CardService {

    private final GroupService groupService;
    private final CardRepository cardRepository;
    private final CardItemRepository cardItemRepository;
    private final CardGroupRepository cardGroupRepository;
    private final ItemRepository itemRepository;

    public CardService(GroupService groupService, CardRepository cardRepository, CardItemRepository cardItemRepository, CardGroupRepository cardGroupRepository, ItemRepository itemRepository) {
        this.groupService = groupService;
        this.cardRepository = cardRepository;
        this.cardItemRepository = cardItemRepository;
        this.cardGroupRepository = cardGroupRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        final Card card = request.toCard();
        cardRepository.save(card);

        final List<CardItem> cardItems = getCardItems(request, card.getId());
        cardItemRepository.saveAll(cardItems);

        return CardResponse.of(card, cardItems, Collections.emptyList());
    }



    @Transactional
    public CardResponse createInGroup(Long groupId, CardRequest request) {
        final Card card = request.toCard();
        cardRepository.save(card);
        final List<CardItem> cardItems = getCardItems(request, card.getId());
        cardItemRepository.saveAll(cardItems);
        return CardResponse.of(card, cardItems, List.of(groupService.enter(groupId, card)));
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, cardItems, groups);
    }

    @Transactional(readOnly = true)
    public List<CardSimpleResponse> findAllCardsByGroup(Long groupId) {
        final List<CardGroup> cardGroups = cardGroupRepository.findAllByGroupId(groupId);
        final List<Card> cardsInGroup = cardGroups.stream().map(CardGroup::getCard).collect(Collectors.toList());
        return CardSimpleResponse.listOf(cardsInGroup);
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = cardRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        card.update(request.toCard());

        final List<CardItem> cardItems = getCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);

        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, cardItems, groups);
    }

    private List<CardItem> getCardItems(CardRequest request, Long cardId) {
        return request.getCardItems().stream()
                .map(it -> new CardItem(cardId, itemRepository.findById(it.getItemId()).get(), it.getValue()))
                .collect(Collectors.toList());
    }
}
