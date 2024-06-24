package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.*;

public class CadastraItemTest
{

    @Mock
    CadastraItem cadastraItem;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarItem() {
        //arrange
        ItemInsertDTO itemDTO = gerarItem();
        when(cadastraItem.execute(any(ItemInsertDTO.class))).thenReturn(itemGerado(itemDTO));

        //act
        var resultado = cadastraItem.execute(itemDTO);

        //assert
        Assertions.assertThat(resultado).isNotNull().isInstanceOf(Item.class);
        Assertions.assertThat(resultado.getDescricao()).isEqualTo(itemDTO.descricao());
        Assertions.assertThat(resultado.getDataAtualizacao()).isNotNull();
    }
}
