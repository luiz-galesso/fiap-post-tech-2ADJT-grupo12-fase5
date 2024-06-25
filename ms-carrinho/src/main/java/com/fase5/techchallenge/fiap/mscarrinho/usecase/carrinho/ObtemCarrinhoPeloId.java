package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway.CarrinhoGateway;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtemCarrinhoPeloId {

    private final CarrinhoGateway carrinhoGateway;

    public Carrinho execute(String id) {

        Carrinho carrinho = carrinhoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Não foi encontrado o carrinho cadastrado com o id informado."));

        return carrinho;
    }
}
