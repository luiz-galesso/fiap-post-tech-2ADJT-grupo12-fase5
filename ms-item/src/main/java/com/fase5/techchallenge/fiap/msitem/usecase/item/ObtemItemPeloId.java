package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObtemItemPeloId {
    private final ItemGateway itemGateway;

    public ObtemItemPeloId(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public Item execute(Long id) {
        return this.itemGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Item não localizado"));
    }


}