package com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller;

import com.fase5.techchallenge.fiap.msitem.entity.item.gateway.ItemGateway;
import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.EstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.usecase.item.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.fase5.techchallenge.fiap.msitem.utils.ItemHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTest {

    @Mock
    CadastraItem cadastraItem;

    @Mock
    AtualizaItem atualizaItem;

    @Mock
    ObtemItemPeloId obtemItemPeloId;

    @Mock
    RemoveItemPeloId removeItemPeloId;

    @Mock
    AumentaEstoqueItem aumentaEstoqueItem;

    @Mock
    ConsomeEstoqueItem consomeEstoqueItem;

    @Mock
    ConsomeEstoquesMassivamente consomeEstoquesMassivamente;

    @Mock
    AumentaEstoquesMassivamente aumentaEstoquesMassivamente;

    @Mock
    ObtemListaItensComEstoque obtemListaItensComEstoque;
    @Mock
    ItemGateway itemGateway;


    MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ItemController itemController = new ItemController(
                cadastraItem,
                atualizaItem,
                obtemItemPeloId,
                removeItemPeloId,
                aumentaEstoqueItem,
                consomeEstoqueItem,
                consomeEstoquesMassivamente,
                aumentaEstoquesMassivamente,
                obtemListaItensComEstoque
        );
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirCadastrarItem() throws Exception {
        //arrange
        ItemInsertDTO itemInsertDTO = gerarItem();
        Item item = itemGerado(gerarItem(), 1L);
        when(cadastraItem.execute(any(ItemInsertDTO.class))).thenReturn(item);

        //act
        mockMvc.perform(
                post("/itens")
                        .content(asJsonString(itemInsertDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        //assert
        verify(cadastraItem, times(1)).execute(any(ItemInsertDTO.class));
    }

    @Test
    void devePermitirAtualizarItem() throws Exception {
        //arrange
        ItemUpdateDTO itemUpdateDTO = gerarItemUpdate(gerarItem());
        Item item = itemGerado(gerarItem(), 1L);
        when(atualizaItem.execute(any(Long.class), any(ItemUpdateDTO.class)))
                .thenReturn(item);
        //act
        mockMvc.perform(
                put("/itens/{id}", 1L)
                        .content(asJsonString(itemUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted());

        //assert
        verify(atualizaItem, times(1))
                .execute(any(Long.class), any(ItemUpdateDTO.class));
    }

    @Test
    void devePermitirBuscarPeloId() throws Exception {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(obtemItemPeloId.execute(any(Long.class))).thenReturn(item);

        //act
        mockMvc.perform(
                get("/itens/{id}", 1L)
        ).andExpect(status().isOk());

        //assert
        verify(obtemItemPeloId, times(1)).execute(any(Long.class));
    }

    @Test
    void devePermitirApagarItem() throws Exception {
        //arrange
        when(removeItemPeloId.execute(any(Long.class))).thenReturn(true);

        //act
        mockMvc.perform(
                delete("/itens/{id}", 1L)
        ).andExpect(status().isOk());

        //assert
        verify(removeItemPeloId, times(1)).execute(any(Long.class));
    }

    @Test
    void devePermitirAumentarEstoqueDeUmItem() throws Exception {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(itemGateway.findById(1L)).thenReturn(Optional.of(item));
        when(aumentaEstoqueItem.execute(
                any(java.lang.Long.class),
                any(java.lang.Long.class)
        )).thenReturn(item);
        when(aumentaEstoqueItem
                .execute(any(Long.class), any(Long.class))).thenReturn(item);

        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        //act
        mockMvc.perform(
                put("/itens/{id}/aumenta-estoque", 1L)
                        .content(asJsonString(estoqueDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        verify(aumentaEstoqueItem, times(1)).execute(any(Long.class), any(Long.class));
    }

    @Test
    void devePermitirConsumirEstoqueDeUmItem() throws Exception {
        //arrange
        Item item = itemGerado(gerarItem(), 1L);
        when(itemGateway.findById(1L)).thenReturn(Optional.of(item));
        when(consomeEstoqueItem.execute(
                any(java.lang.Long.class),
                any(java.lang.Long.class)
        )).thenReturn(item);
        when(consomeEstoqueItem
                .execute(any(Long.class), any(Long.class))).thenReturn(item);

        EstoqueDTO estoqueDTO = new EstoqueDTO(10L);

        //act
        mockMvc.perform(
                put("/itens/{id}/consome-estoque", 1L)
                        .content(asJsonString(estoqueDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        verify(consomeEstoqueItem, times(1)).execute(any(Long.class), any(Long.class));
    }
}
