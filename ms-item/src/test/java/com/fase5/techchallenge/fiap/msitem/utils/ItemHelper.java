package com.fase5.techchallenge.fiap.msitem.utils;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ItemHelper {

    public static ItemInsertDTO gerarItem() {
        return new ItemInsertDTO(
                "Smartphone Galaxy S21",
                "Samsung",
                "Telefones",
                50L,
                2000D
        );
    }

    public static ItemInsertDTO gerarItem(String descricao) {
        return new ItemInsertDTO(
                descricao,
                "Samsung",
                "Telefones",
                50L,
                2000D
        );
    }

    public static Item itemGerado(ItemInsertDTO pDTO) {
        return new Item(
                pDTO.descricao(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario(),
                LocalDateTime.now());
    }

    public static ItemUpdateDTO gerarItemUpdate(ItemInsertDTO pDTO)
    {
        return new ItemUpdateDTO(
                pDTO.descricao(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario());
    }

    public static Item itemGerado(ItemInsertDTO pDTO, Long id) {
        return new Item(
                id,
                pDTO.descricao(),
                pDTO.marca(),
                pDTO.categoria(),
                pDTO.quantidade(),
                pDTO.valorUnitario(),
                LocalDateTime.now());
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(object);
    }

}
