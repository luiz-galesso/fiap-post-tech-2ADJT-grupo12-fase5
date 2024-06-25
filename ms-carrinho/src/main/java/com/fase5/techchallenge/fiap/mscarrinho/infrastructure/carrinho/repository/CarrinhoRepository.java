package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.repository;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {

}
