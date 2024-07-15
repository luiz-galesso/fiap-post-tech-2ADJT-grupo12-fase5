package com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.controller.dto;

import jakarta.validation.constraints.NotNull;

public record PagamentoRequestDTO(
        Double valor,
        Long numeroCartao,
        String validadeCartao,
        String nomeCartao,
        String cvvCartao
) {
};
