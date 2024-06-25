package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;


import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculaValorTotal {
    public double execute(List<Item> itens) {
        double valorTotal = 0;

        for (Item item : itens) {
            valorTotal = valorTotal + item.getValorUnitario() * item.getQuantidade();
        }
        return valorTotal;
    }
}
