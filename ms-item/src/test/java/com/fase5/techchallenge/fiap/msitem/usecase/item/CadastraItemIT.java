package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.utils.ItemHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class CadastraItemIT
{

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirCadastrarItem() {
        //arrange
        ItemInsertDTO itemDTO = ItemHelper.gerarItem();

        //act
        var resultado = cadastraItem.execute(itemDTO);

        //assert
        assertThat(resultado).isNotNull().isInstanceOf(Item.class);
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getDataAtualizacao()).isNotNull();
    }

}
