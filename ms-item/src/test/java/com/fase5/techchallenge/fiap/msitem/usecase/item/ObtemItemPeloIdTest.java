package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.itemGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ObtemItemPeloIdTest
{

    @Mock
    ObtemItemPeloId obtemItemPeloId;

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
    void devePermitirObterItemPeloId() {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(obtemItemPeloId.execute(any(Long.class))).thenReturn(item);

        //act
        var resultado = obtemItemPeloId.execute(1L);

        //assert
        Assertions.assertThat(resultado).isNotNull().isInstanceOf(Item.class);
        Assertions.assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void deveGerarExcecao_QuandoOItemNaoForEncontrado() {
        //act
        Mockito.doThrow(new EntityNotFoundException("Item não localizado"))
                .when(obtemItemPeloId).execute(any(Long.class));

        //assert
        assertThatThrownBy(() -> obtemItemPeloId.execute(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Item não localizado");
    }

}
