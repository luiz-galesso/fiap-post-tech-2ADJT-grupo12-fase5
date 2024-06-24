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

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.itemGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ConsomeEstoqueItemTest {

    @Mock
    ConsomeEstoqueItem consomeEstoqueItem;

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
    void devePermitirConsumirEstoqueItem() {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(itemGateway.findById(1L)).thenReturn(Optional.of(item));
        when(consomeEstoqueItem.execute(
                any(Long.class),
                any(Long.class)
        )).thenReturn(item);

        //act
        var resultado = consomeEstoqueItem.execute(1L, 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(item.getId());
    }

    @Test
    void deveGerarExcecao_QuandoItemnaoEncontrato() {
        //act
        Long id = 2L;

        Mockito.doThrow(new BussinessErrorException("Item com id " + id + "não foi encontrado."))
                .when(consomeEstoqueItem).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueItem.execute(id, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Item com id " + id + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoQuantidadeInvalida() {
        //act
        Long id = 1L;

        doThrow(new BussinessErrorException("Quantidade inválida."))
                .when(consomeEstoqueItem).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueItem.execute(id, 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

    @Test
    void deveGerarExecao_QuandoEstoqueInsuficiente() {
        //act
        Long id = 2L;

        doThrow(new BussinessErrorException("Estoque insuficiente."))
                .when(consomeEstoqueItem).execute(any(Long.class), any(Long.class));

        //assert
        assertThatThrownBy(() -> consomeEstoqueItem.execute(id, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Estoque insuficiente.");
    }
}
