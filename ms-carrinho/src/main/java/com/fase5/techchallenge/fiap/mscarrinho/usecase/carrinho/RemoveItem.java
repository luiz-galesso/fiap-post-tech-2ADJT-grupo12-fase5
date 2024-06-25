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
public class RemoveItem {

    private final CarrinhoGateway carrinhoGateway;

    private final CalculaValorTotal calculaValorTotal;

    public Carrinho execute(String id, Long idItem) {

        Carrinho carrinho = carrinhoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Não foi encontrado o carrinho cadastrado com o id informado."));

        for (int index = 0; index < carrinho.getItens().size(); index++) {
            if (Objects.equals(carrinho.getItens().get(index).getIdItem(), idItem)) {
                Item item = carrinho.getItens().remove(index);
                }
            }
        carrinho.setValorTotal(calculaValorTotal.execute(carrinho.getItens()));
        return carrinhoGateway.update(carrinho);
    }
}
