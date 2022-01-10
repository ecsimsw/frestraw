package com.example.frestraw.card.domain;

import com.example.frestraw.card.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
