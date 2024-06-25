package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway.CarrinhoGateway;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LimpaCarrinho {

    private final CarrinhoGateway carrinhoGateway;

    private final CalculaValorTotal calculaValorTotal;

    public void execute(String id) {

        carrinhoGateway.remove(id);

    }
}
