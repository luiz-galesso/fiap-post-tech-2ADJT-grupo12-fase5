package com.fase5.techchallenge.fiap.msitem.usecase.item;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.usecase.exception.BussinessErrorException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.gerarItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
public class ConsomeEstoqueItemIT {

    @Autowired
    ConsomeEstoqueItem consomeEstoqueItem;

    @Autowired
    CadastraItem cadastraItem;

    @Test
    void devePermitirConsumirEstoqueItem() {
        //arrange
        Item item = cadastraItem.execute(gerarItem());

        //act
        var resultado = consomeEstoqueItem
                .execute(item.getId(), 10L);

        //assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getQuantidade()).isEqualTo(item.getQuantidade());
    }

    @Test
    void deveGerarExcecao_QuandoItemnaoEncontrato() {
        //assert
        assertThatThrownBy(() -> consomeEstoqueItem
                .execute(888L, 10L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Item com id " + 888L + "não foi encontrado.");
    }

    @Test
    void deveGerarExcecao_QuandoQuantidadeInvalida() {
        Item item = cadastraItem.execute(gerarItem("Item " + new Random().nextLong()));
        //assert
        assertThatThrownBy(() -> consomeEstoqueItem
                .execute(item.getId(), 0L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Quantidade inválida.");
    }

    @Test
    void deveGerarExecao_QuandoEstoqueInsuficiente() {
        Item item = cadastraItem.execute(gerarItem("Item " + new Random().nextLong()));
        //assert
        assertThatThrownBy(() -> consomeEstoqueItem
                .execute(item.getId(), 1000L))
                .isInstanceOf(BussinessErrorException.class)
                .hasMessage("Estoque insuficiente.");
    }
}
