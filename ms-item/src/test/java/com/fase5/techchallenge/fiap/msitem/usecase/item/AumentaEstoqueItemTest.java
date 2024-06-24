package com.fase5.techchallenge.fiap.msitem.usecase.item;


import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.*;
import static org.mockito.Mockito.*;

public class AumentaEstoqueItemTest {

    @Mock
    AumentaEstoqueItem aumentaEstoqueItem;

    @Mock
    ItemGateway itemGateway;

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
    void devePermitirAumentarEstoqueDoItem() {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(itemGateway.findById(1L)).thenReturn(Optional.of(item));
        when(aumentaEstoqueItem.execute(
                any(Long.class),
                any(Long.class)
        )).thenReturn(item);

        //act
        var resultado = aumentaEstoqueItem.execute(1L, 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(item.getId());
    }

    @Test
    void deveGerarExcecao_QuandoItemNaoEncontrado() {
        //act
        Mockito.doThrow(new BussinessErrorException("Item com id " + 1L + "não foi encontrado."))
                .when(aumentaEstoqueItem).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> aumentaEstoqueItem.execute(1L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Item com id " + 1L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAQuantidadeForMenorIgualZero() {
        //act
        doThrow(new BussinessErrorException("Quantidade inválida."))
                .when(aumentaEstoqueItem).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> aumentaEstoqueItem.execute(1L, 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

}
