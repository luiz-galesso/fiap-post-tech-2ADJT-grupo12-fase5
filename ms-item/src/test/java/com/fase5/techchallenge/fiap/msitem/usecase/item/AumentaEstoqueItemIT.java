package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class AumentaEstoqueItemIT {
    @Autowired
    AumentaEstoqueItem aumentaEstoqueItem;

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirAumentarEstoqueDoItem() {
        //arrange
        Item item = cadastraItem.execute(gerarItem());

        //act
        var resultado = aumentaEstoqueItem
                .execute(item.getId(), 10L);

        //assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getQuantidade()).isEqualTo(item.getQuantidade());
    }

    @Test
    void deveGerarExcecao_QuandoNaoEncontrado() {
        //assert
        assertThatThrownBy(() -> aumentaEstoqueItem
                .execute(888L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Item com id " + 888L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoAQuantidadeForMenorIgualZero() {
        //arrange
        Item item = cadastraItem.execute(gerarItem());

        //assert
        assertThatThrownBy(() -> aumentaEstoqueItem
                .execute(item.getId(), 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }
}
