package com.example.frestraw.card;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.frestraw.card.item.ItemRepository;
import com.example.frestraw.group.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public CardResponse create(CardRequest request, MultipartFile multipartFile) throws IOException {

        final String imageName = multipartFile == null ? "default" : StringUtils.cleanPath(multipartFile.getOriginalFilename());

        final Card card = request.toCard(imageName);
        cardRepository.save(card);

        final List<CardItem> cardItems = getRequestCardItems(request, card.getId());
        cardItemRepository.saveAll(cardItems);


        String uploadDir = "card-photos/" + card.getId();
        if ("default".equals(imageName)) {
//            String uploadPath = uploadDir.toFile().getAbsolutePath();
//            multipartFile = Files.
        }
        FileUploadUtil.saveFile(uploadDir, imageName, multipartFile);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), Collections.emptyList());
    }

    @Transactional
    public CardResponse createInGroup(Long groupId, CardRequest request, MultipartFile multipartFile) throws IOException {
        final String imageName = multipartFile == null ? "default" : StringUtils.cleanPath(multipartFile.getOriginalFilename());
        final Card card = cardRepository.save(request.toCard(imageName));
        final List<CardItem> cardItems = cardItemRepository.saveAll(getRequestCardItems(request, card.getId()));
        final GroupResponse groupResponse = groupService.enter(groupId, card);

        String uploadDir = "card-photos/" + card.getId();
        if ("default".equals(imageName)) {
//            String uploadPath = uploadDir.toFile().getAbsolutePath();
//            multipartFile = Files.
        }
        FileUploadUtil.saveFile(uploadDir, imageName, multipartFile);

        return CardResponse.of(card, CardItemResponse.listOf(cardItems), List.of(groupResponse));
    }

    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        final Card card = getCard(id);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groups);
    }

    @Transactional(readOnly = true)
    public CardResponse findWithComparing(Long targetId, Long myId) {
        final Card target = getCard(targetId);
        final Card mine = getCard(myId);

        final List<CardItem> targetItems = cardItemRepository.findAllByCardId(target.getId());
        final List<CardItem> myItems = cardItemRepository.findAllByCardId(mine.getId());

        final List<CardItemResponse> responses = targetItems.stream()
                .map(it -> compareWithMine(it, myItems))
                .collect(Collectors.toList());

        return CardResponse.of(target, responses, groupService.findAllByCard(target));
    }

    private CardItemResponse compareWithMine(CardItem targetItem, List<CardItem> myItems) {
        if(isIncluded(targetItem, myItems)) {
            return CardItemResponse.sameHere(targetItem);
        }
        return CardItemResponse.of(targetItem);
    }

    private boolean isIncluded(CardItem targetItem, List<CardItem> myItems) {
        return myItems.stream().anyMatch(targetItem::isSame);
    }

    @Transactional(readOnly = true)
    public List<CardSimpleResponse> findAllCardsByGroup(Long groupId) {
        final List<CardGroup> cardGroups = cardGroupRepository.findAllByGroupId(groupId);
        final List<Card> cardsInGroup = cardGroups.stream().map(CardGroup::getCard).collect(Collectors.toList());
        return CardSimpleResponse.listOf(cardsInGroup);
    }

    @Transactional
    public CardResponse update(Long id, CardRequest request) {
        final Card card = getCard(id);
        card.update(request.toCard("nothing"));

        final List<CardItem> cardItems = getRequestCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);

        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groups);
    }

    private List<CardItem> getRequestCardItems(CardRequest request, Long cardId) {
        return request.getCardItems().stream()
                .map(it -> new CardItem(cardId, itemRepository.findById(it.getItemId()).get(), it.getValue()))
                .collect(Collectors.toList());
    }

    private Card getCard(Long targetId) {
        return cardRepository.findById(targetId).orElseThrow(IllegalArgumentException::new);
    }
}
