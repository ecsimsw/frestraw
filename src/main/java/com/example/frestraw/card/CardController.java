package com.example.frestraw.card;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(@RequestBody CardRequest cardRequest) {
        final CardResponse response = cardService.create(cardRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<CardResponse> createInGroup(@PathVariable Long groupId, @RequestBody CardRequest cardRequest) {
        final CardResponse response = cardService.createInGroup(groupId, cardRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<CardSimpleResponse>> cardsInGroup(@PathVariable Long groupId) {
        final List<CardSimpleResponse> responses = cardService.findAllCardsByGroup(groupId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> findCardById(@PathVariable Long cardId, @CookieValue(required = false) Long myId) {
        if (Objects.isNull(myId)) {
            final CardResponse response = cardService.findById(cardId);
            return ResponseEntity.ok(response);
        }
        final CardResponse response = cardService.findWithComparing(cardId, myId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable Long id, @RequestBody CardRequest request) {
        final CardResponse response = cardService.update(id, request);
        return ResponseEntity.ok(response);
    }
}
