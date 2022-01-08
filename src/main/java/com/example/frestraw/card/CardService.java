package com.example.frestraw.card;

import com.example.frestraw.card.item.ItemRepository;
import com.example.frestraw.group.CardGroup;
import com.example.frestraw.group.CardGroupRepository;
import com.example.frestraw.group.GroupResponse;
import com.example.frestraw.group.GroupService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        String uploadDir = "card-photos/" + card.getId();
        if ("default".equals(imageName)) {
//            String uploadPath = uploadDir.toFile().getAbsolutePath();
//            multipartFile = Files.
        }
        FileUploadUtil.saveFile(uploadDir, imageName, multipartFile);
        return CardResponse.of(card, Collections.emptyList(), Collections.emptyList());
    }

    @Transactional
    public CardResponse createInGroup(Long groupId, CardRequest request, MultipartFile multipartFile) throws IOException {
        final String imageName = multipartFile == null ? "default" : StringUtils.cleanPath(multipartFile.getOriginalFilename());
        final Card card = cardRepository.save(request.toCard(imageName));
        final List<GroupResponse> groupResponses = groupService.enter(groupId, card);

        String uploadDir = "card-photos/" + card.getId();
        if ("default".equals(imageName)) {
//            String uploadPath = uploadDir.toFile().getAbsolutePath();
//            multipartFile = Files.
        }
        FileUploadUtil.saveFile(uploadDir, imageName, multipartFile);

        return CardResponse.of(card, Collections.emptyList(), groupResponses);
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
        card.update(request.toCard(card.getPhotosImagePath()));

        final List<CardItem> cardItems = getRequestCardItems(request, id);
        cardItemRepository.deleteAllByCardId(id);
        cardItemRepository.saveAll(cardItems);

        final List<GroupResponse> groups = groupService.findAllByCard(card);
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groups);
    }

    private List<CardItem> getRequestCardItems(CardRequest request, Long cardId) {
        if (request.getCardItems() == null) {
            return Collections.emptyList();
        }
        return request.getCardItems().stream()
                .map(it -> new CardItem(cardId, itemRepository.findById(it.getItemId()).get(), it.getValue()))
                .collect(Collectors.toList());
    }

    private Card getCard(Long targetId) {
        return cardRepository.findById(targetId).orElseThrow(IllegalArgumentException::new);
    }

    public CardResponse enterInGroup(Long cardId, Long groupId) {
        final Card card = getCard(cardId);
        final List<GroupResponse> groupResponses = groupService.enter(groupId, card);
        final List<CardItem> cardItems = cardItemRepository.findAllByCardId(card.getId());
        return CardResponse.of(card, CardItemResponse.listOf(cardItems), groupResponses);
    }

    public byte[] imageById(Long cardId) throws IOException {
        final Card card = cardRepository.getById(cardId);
        final File imageFile = new File(card.getId() + ".png");
        return getBytesFromFile(imageFile);
    }

    // Returns the contents of the file in a byte array.
    private byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }
}
