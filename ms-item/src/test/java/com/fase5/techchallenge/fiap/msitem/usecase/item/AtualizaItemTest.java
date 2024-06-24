package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class AtualizaItemTest
{
    @Mock
    AtualizaItem atualizaItem;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void devePermitirAtualizarItem() {
        //arrange
        ItemUpdateDTO itemUpdateDTO = gerarItemUpdate(gerarItem());
        Item item = itemGerado(gerarItem(), 1L);
        when(atualizaItem.execute(any(Long.class), any(ItemUpdateDTO.class)))
                .thenReturn(item);

        //act
        var resultado = atualizaItem.execute(1L, itemUpdateDTO);

        //assert
        Assertions.assertThat(resultado).isNotNull().isInstanceOf(Item.class);
        Assertions.assertThat(resultado.getId()).isEqualTo(item.getId());
    }

    @Test
    void deveGerarExcecao_QuandoItemNaoEncontrado() {
        //act
        Mockito.doThrow(new BussinessErrorException("Não foi encontrado o item cadastrado com o identificador informado."))
                .when(atualizaItem).execute(any(Long.class), any(ItemUpdateDTO.class));

        //assert
        assertThatThrownBy(() -> atualizaItem.execute(1L, gerarItemUpdate(gerarItem())))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o item cadastrado com o identificador informado.");
    }
}
