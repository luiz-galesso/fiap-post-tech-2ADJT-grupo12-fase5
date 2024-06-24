package com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pagamento")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    private Long id;

}
