package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagamentoDTO {
   private Double valor;
   private Long numeroCartao;
   private String validadeCartao;
   private String nomeCartao;
   private String cvvCartao;
}
