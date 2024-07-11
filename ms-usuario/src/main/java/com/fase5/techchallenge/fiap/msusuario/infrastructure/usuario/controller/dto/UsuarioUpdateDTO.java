package com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioUpdateDTO {

    private String nome;
    private LocalDate dataNascimento;

}
