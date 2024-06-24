package com.fase5.techchallenge.fiap.mscliente.utils;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;

public class EnderecoHelper {
    public static Endereco gerarEndereco(Cliente cliente) {
        return Endereco.builder()
                .id(1)
                .cliente(cliente)
                .logradouro("Rua perto da esquina")
                .numero("10")
                .bairro("Vila São João")
                .complemento("Casa 2")
                .cep(92910391L)
                .cidade("São Cristovão")
                .estado("Rio de Janeiro")
                .referencia("Proximo a Casa Azul")
                .build();
    }

    public static EnderecoInsertDTO gerarEnderecoInsert() {
        return new EnderecoInsertDTO("Rua perto da esquina", "10", "Vila São João", "Casa 2", 92910391L, "São Cristovão", "Rio de Janeiro", "Proximo a Casa Azul");
    }

    public static EnderecoUpdateDTO gerarEnderecoUpdate() {
        return new EnderecoUpdateDTO("Rua do O", "20", "Nova Zela", "AP 13A", 4148949L, "São Joao Meriti", "Rio de Janeiro", "Proximo ao Mercado");
    }

}