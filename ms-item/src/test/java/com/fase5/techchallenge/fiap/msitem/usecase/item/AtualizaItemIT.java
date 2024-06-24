package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.msitem.utils.ItemHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class AtualizaItemIT
{

    @Autowired
    AtualizaItem atualizaItem;

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirAtualizarItem() {
        //arrange
        Item item = cadastraItem.execute(ItemHelper.gerarItem());

        //act
        var resultado = atualizaItem
                .execute(item.getId(), ItemHelper.gerarItemUpdate(ItemHelper.gerarItem()));

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isInstanceOf(Item.class);
        assertThat(resultado.getId()).isEqualTo(item.getId());
    }

    @Test
    void deveGerarExcecao_QuandoItemNaoEncontrado() {
        //assert
        assertThatThrownBy(() -> atualizaItem
                .execute(1010L, ItemHelper.gerarItemUpdate(ItemHelper.gerarItem())))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Não foi encontrado o item cadastrado com o identificador informado.");
    }
}
