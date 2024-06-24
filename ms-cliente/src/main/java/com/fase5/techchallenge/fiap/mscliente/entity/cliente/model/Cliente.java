package com.fase5.techchallenge.fiap.mscliente.entity.cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    private String email;

    @NotNull
    private String nome;

    @NotNull
    private LocalDateTime dataRegistro;

    private LocalDate dataNascimento;

}
