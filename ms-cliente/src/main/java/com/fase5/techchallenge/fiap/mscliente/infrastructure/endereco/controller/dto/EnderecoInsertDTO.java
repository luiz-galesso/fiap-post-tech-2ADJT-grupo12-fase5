package com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnderecoInsertDTO {

    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private Long cep;

    private String cidade;

    private String estado;

    private String referencia;
}
