package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto;

public record RealizaPagamentoDTO(
        Long numeroCartao,
        String validadeCartao,
        String nomeCartao,
        String cvvCartao
        ) {
}
