package com.fase5.techchallenge.fiap.msitem.infrastructure.item.repository;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByQuantidadeGreaterThan(Long quantidade);

}
