package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway.CarrinhoGateway;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoveQuantidadeItem {

    private final CarrinhoGateway carrinhoGateway;

    private final CalculaValorTotal calculaValorTotal;

    private final RemoveItem removeItem;

    public Carrinho execute(String id, Long idItem, Long quantidade) {

        Carrinho carrinho = carrinhoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Não foi encontrado o carrinho cadastrado com o id informado."));
        List<Item> itens = carrinho.getItens();
        for (int index = 0; index < itens.size(); index++) {
            if (Objects.equals(itens.get(index).getIdItem(), idItem)) {
                Item item = itens.get(index);
                Long quantidadeNova = item.getQuantidade() - quantidade;
                if (quantidadeNova <= 0) {
                    carrinho = removeItem.execute(id, idItem);
                } else {
                    item.setQuantidade(quantidadeNova);
                    carrinho.getItens().set(index, item);
                }
            }
        }
        carrinho.setValorTotal(calculaValorTotal.execute(carrinho.getItens()));
        return carrinhoGateway.update(carrinho);

    }
}
