package com.fase5.techchallenge.fiap.msitem.entity.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_generator")
    @SequenceGenerator(name = "item_generator", sequenceName = "item_sequence", allocationSize = 1)
    private Long id;
    @NotBlank

    private String descricao;
    @NotBlank
    private String marca;
    @NotBlank
    private String categoria;
    @NotNull
    private Long quantidade;
    @NotNull
    private double valorUnitario;

    private LocalDateTime dataAtualizacao;

    public Item(String descricao, String marca, String categoria, Long quantidade, double valorUnitario, LocalDateTime dataAtualizacao) {
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataAtualizacao = dataAtualizacao;
    }
}
