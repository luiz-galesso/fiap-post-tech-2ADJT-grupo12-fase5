package com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ItemInsertDTO(
        @NotNull
        String descricao,
        @NotNull
        String marca,
        @NotNull
        String categoria,
        @NotNull
        Long quantidade,

        @NotNull
        double valorUnitario

) {


};
