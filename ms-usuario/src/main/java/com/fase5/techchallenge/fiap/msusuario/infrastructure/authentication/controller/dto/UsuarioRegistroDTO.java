package com.fase5.techchallenge.fiap.msusuario.infrastructure.authentication.controller.dto;

import java.time.LocalDate;


public record UsuarioRegistroDTO (
    String email,
    String nome,
    String password,

    String role,
    LocalDate dataNascimento){
}
