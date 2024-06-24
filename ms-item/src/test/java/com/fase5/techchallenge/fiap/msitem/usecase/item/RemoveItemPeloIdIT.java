package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.itemGerado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class RemoveItemPeloIdIT
{
    @Autowired
    RemoveItemPeloId removeItemPeloId;

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirRemoverItemPeloId() {
        //arrange
        Item item = cadastraItem.execute(gerarItem());

        //act
        var resultado = removeItemPeloId.execute(item.getId());

        //assert
        assertThat(resultado).isTrue();
    }

    @Test
    void deveGerarExcecao_QuandoItemNaoExistir() {
        //arrange
        Item item = itemGerado(gerarItem(), 1010L);

        //assert
        assertThatThrownBy(() -> removeItemPeloId.execute(item.getId())).
                isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o item cadastrado com o ID informado.");
    }

}
