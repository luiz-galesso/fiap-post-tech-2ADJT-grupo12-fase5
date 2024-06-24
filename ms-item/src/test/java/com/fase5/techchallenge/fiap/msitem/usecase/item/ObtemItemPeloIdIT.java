package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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
public class ObtemItemPeloIdIT
{
    @Autowired
    ObtemItemPeloId obtemItemPeloId;

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirObterItemPeloId() {
        //arrange
        Item item = cadastraItem.execute(gerarItem());

        //act
        var resultado = obtemItemPeloId.execute(item.getId());

        //assert
        Assertions.assertThat(resultado).isNotNull().isInstanceOf(Item.class);
        Assertions.assertThat(resultado.getId()).isEqualTo(item.getId());
    }

    @Test
    void deveGerarExcecao_QuandoOItemNaoForEncontrado() {
        //assert
        assertThatThrownBy(() -> obtemItemPeloId.execute(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Item não localizado");
    }
}
