package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtemListaItensComEstoque {
    private final ItemGateway itemGateway;

    public List<Item>  execute() {
        return this.itemGateway.findByQuantidadeGreaterThan(0L);
    }


}