package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto;

import java.io.Serializable;

public record RemoveQuantidadeItemDTO(Long idItem, Long quantidade) implements Serializable {

}
