package com.fase5.techchallenge.fiap.msitem.entity.item.gateway;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ItemGateway {

    private ItemRepository itemRepository;

    public ItemGateway(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(Item item) {
        return this.itemRepository.save(item);
    }

    public Item update(Item item) {
        return this.itemRepository.save(item);
    }

    public Optional<Item> findById(Long id) {
        return this.itemRepository.findById(id);
    }

    public void remove(Long codItem) {
        itemRepository.deleteById(codItem);
    }

    public List<Item> findByQuantidadeGreaterThan(Long quantidade) {
        return itemRepository.findByQuantidadeGreaterThan(quantidade);
    }

}