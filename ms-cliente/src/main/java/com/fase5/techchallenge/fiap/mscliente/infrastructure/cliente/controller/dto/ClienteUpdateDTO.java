package com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteUpdateDTO {

    private String nome;
    private LocalDate dataNascimento;

}
