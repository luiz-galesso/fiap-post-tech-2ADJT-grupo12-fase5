package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizaItem {

    private final ItemGateway itemGateway;

    public Item execute(Long codItem, ItemUpdateDTO itemUpdateDTO) {

        Optional<Item> itemOptional = itemGateway.findById(codItem);

        if (itemOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o item cadastrado com o identificador informado.");
        }

        Item item = new Item(codItem,
                itemUpdateDTO.descricao(),
                itemUpdateDTO.marca(),
                itemUpdateDTO.categoria(),
                itemUpdateDTO.quantidade(),
                itemUpdateDTO.valorUnitario(),
                LocalDateTime.now()
        );

        return this.itemGateway.update(item);
    }
}
