package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
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

public class RemoveItemPeloIdTest
{

    @Mock
    RemoveItemPeloId removeItemPeloId;

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
    void devePermitirRemoverItemPeloId() {

        //arrange
        Item item = itemGerado(gerarItem(), 1L);

        when(removeItemPeloId.execute(any(Long.class))).thenReturn(true);

        //act
        var resultado =  removeItemPeloId.execute(item.getId());

        //assert
        assertThat(resultado).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoItemNaoExistir() {

        //arrange
        Item item = itemGerado(gerarItem(), 1L);

        //act
        Mockito.doThrow(new BussinessErrorException("Não foi encontrado o item cadastrado com o ID informado."))
                .when(removeItemPeloId).execute(any(Long.class));

        //assert
        assertThatThrownBy(() -> removeItemPeloId.execute(item.getId()))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o item cadastrado com o ID informado.");
    }
}
