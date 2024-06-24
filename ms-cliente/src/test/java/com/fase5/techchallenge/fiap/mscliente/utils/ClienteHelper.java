package com.fase5.techchallenge.fiap.mscliente.utils;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteHelper {
    private static final Faker faker = new Faker();

    public static Cliente gerarCliente() {
        return Cliente.builder()
                .email("joao-silva@email.com")
                .nome("João Silva")
                .dataNascimento(LocalDate.of(1991, 06, 22))
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    public static ClienteInsertDTO gerarClienteInsert() {
        return new ClienteInsertDTO("joao-silva@email.com", "João Silva", LocalDate.of(1991, 06, 22));
    }

    public static ClienteUpdateDTO gerarClienteUpdate() {
        return new ClienteUpdateDTO("Joao dos Santos", LocalDate.of(1990, 9, 12));
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

    public static String gerarNomeAleatorio() {
        String nome = faker.name().firstName() + " " + faker.name().lastName();
        return nome;
    }
}
