package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PagamentoResponseDTO {

   private Long id;

   private double valor;

   private String idUsuario;

   private String situacao;

   private LocalDateTime dataHoraPagamento;
}
