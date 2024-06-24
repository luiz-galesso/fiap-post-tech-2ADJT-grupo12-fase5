package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemEstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AumentaEstoquesMassivamente {

    private final ItemGateway itemGateway;
    private final AumentaEstoqueItem aumentaEstoqueItem;

    public void execute(List<ItemEstoqueDTO> itemEstoqueList){

        itemEstoqueList.forEach( i -> {
            Item item = itemGateway.findById(i.id()).orElseThrow(() -> new BussinessErrorException("O item "+ i.id()+ " não foi encontrado."));
            aumentaEstoqueItem.execute(item.getId(), i.quantidade());
        });

    }
}
