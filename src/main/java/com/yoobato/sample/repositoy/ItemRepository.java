package com.yoobato.sample.repositoy;

import com.yoobato.sample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
