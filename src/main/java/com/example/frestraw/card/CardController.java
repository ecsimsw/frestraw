package com.example.frestraw.card;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> create(ModelMap map, @RequestParam(value = "image", required = false) MultipartFile multipartFile)
        throws IOException {
        List<CardItem> cardItmes = (List<CardItem>)map.getAttribute("cardItmes");
        System.out.println(cardItmes);
        final CardResponse response = cardService.create(null, multipartFile);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<CardResponse> createInGroup(@PathVariable Long groupId, CardRequest cardRequest, @RequestParam(value = "image", required = false) MultipartFile multipartFile)
        throws IOException {
        final CardResponse response = cardService.createInGroup(groupId, cardRequest, multipartFile);
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
