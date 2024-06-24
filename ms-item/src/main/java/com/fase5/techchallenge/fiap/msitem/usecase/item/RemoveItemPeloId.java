package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RemoveItemPeloId {
    private final ItemGateway itemGateway;

    public RemoveItemPeloId(ItemGateway itemGateway) {
        this.itemGateway = itemGateway;
    }

    public boolean execute(Long id) {
        Optional<Item> itemOptional = itemGateway.findById(id);

        if (itemOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o item cadastrado com o ID informado.");
        }

        itemGateway.remove(id);
        return true;
    }

}