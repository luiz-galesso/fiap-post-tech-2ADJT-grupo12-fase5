package com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pagamento")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_generator")
    @SequenceGenerator(name = "pagamento_generator", sequenceName = "pagamento_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private double valor;

    @NotNull
    private String idUsuario;

    @NotNull
    private String situacao;

    @NotNull
    private LocalDateTime dataHoraPagamento;

}
