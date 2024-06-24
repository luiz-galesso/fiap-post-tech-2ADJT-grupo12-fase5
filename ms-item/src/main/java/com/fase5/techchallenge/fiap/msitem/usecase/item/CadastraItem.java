package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CadastraItem {

    private final ItemGateway itemGateway;

    public Item execute(ItemInsertDTO itemDTO) {

        Item item =
                    new Item(
                            itemDTO.descricao(),
                            itemDTO.marca(),
                            itemDTO.categoria(),
                            itemDTO.quantidade(),
                            itemDTO.valorUnitario(),
                            LocalDateTime.now()
                    );
            return this.itemGateway.create(item);
    }
}
