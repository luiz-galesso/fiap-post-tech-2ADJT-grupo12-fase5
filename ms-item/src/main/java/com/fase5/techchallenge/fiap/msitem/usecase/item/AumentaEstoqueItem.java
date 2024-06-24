package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AumentaEstoqueItem {

    private final ItemGateway itemGateway;

    public Item execute(Long id, Long quantidade) {

        Item item = itemGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Item com id " + id + "não foi encontrado."));

        if (quantidade <= 0) {
            throw new BussinessErrorException("Quantidade inválida.");
        }

        item.setQuantidade(item.getQuantidade() + quantidade);
        item.setDataAtualizacao(LocalDateTime.now());

        return this.itemGateway.update(item);
    }
}
