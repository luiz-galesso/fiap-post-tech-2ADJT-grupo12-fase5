package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDTO {
   private Long id;
   private String descricao;
   private String marca;
   private String categoria;
   private Long quantidade;
   private double valorUnitario;
   private LocalDateTime dataAtualizacao;
}
