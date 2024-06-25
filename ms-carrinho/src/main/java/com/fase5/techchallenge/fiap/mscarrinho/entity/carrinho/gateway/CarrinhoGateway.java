package com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.repository.CarrinhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CarrinhoGateway {

    private CarrinhoRepository carrinhoRepository;

    public Optional<Carrinho> findById(String id) {
        return this.carrinhoRepository.findById(id);
    }

    public Carrinho create(Carrinho carrinho) {
        return this.carrinhoRepository.save(carrinho);
    }

    public Carrinho update(Carrinho carrinho) {
        return this.carrinhoRepository.save(carrinho);
    }

    public void remove(String id) {
        this.carrinhoRepository.deleteById(id);
    }

}