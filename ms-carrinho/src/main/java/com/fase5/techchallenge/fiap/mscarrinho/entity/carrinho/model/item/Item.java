package com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private UUID uuid;

    private Long idItem;

    @ManyToOne
    @JsonIgnore
    private Carrinho carrinho;

    private Long quantidade;

    private double valorUnitario;

}
