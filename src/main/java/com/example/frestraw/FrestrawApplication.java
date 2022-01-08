package com.example.frestraw;

import com.example.frestraw.card.Card;
import com.example.frestraw.card.CardRepository;
import com.example.frestraw.group.CardGroup;
import com.example.frestraw.group.CardGroupRepository;
import com.example.frestraw.group.Group;
import com.example.frestraw.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class FrestrawApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrestrawApplication.class, args);
//
//        SpringApplication application = new SpringApplication(FrestrawApplication.class);
//        final ConfigurableApplicationContext ctx = application.run(args);
//        final Test test = (Test)ctx.getBean("test");
//        test.create();
    }

}

@Component
class Test {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CardGroupRepository cardGroupRepository;

    public void create() {

        final Card card = cardRepository.save(new Card());
        final Group group = groupRepository.save(new Group());

        cardGroupRepository.save(new CardGroup(card, group));
        final List<CardGroup> allByGroupId = cardGroupRepository.findAllByGroupId(group.getId());
        System.out.println("======" + allByGroupId.size());
    }
}
