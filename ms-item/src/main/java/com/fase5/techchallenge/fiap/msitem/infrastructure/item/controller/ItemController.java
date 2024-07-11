package com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller;

import com.fase5.techchallenge.fiap.msitem.entity.item.model.Item;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.EstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemEstoqueDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemInsertDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.item.controller.dto.ItemUpdateDTO;
import com.fase5.techchallenge.fiap.msitem.infrastructure.util.DefaultResponse;
import com.fase5.techchallenge.fiap.msitem.usecase.item.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/itens")
@AllArgsConstructor
@Tag(name = "Itens", description = "Serviços para manipular os itens")
public class ItemController {

    private final CadastraItem cadastraItem;
    private final AtualizaItem atualizaItem;
    private final ObtemItemPeloId obtemItemPeloId;
    private final RemoveItemPeloId removeItemPeloId;
    private final AumentaEstoqueItem aumentaEstoqueItem;
    private final ConsomeEstoqueItem consomeEstoqueItem;
    private final ConsomeEstoquesMassivamente consomeEstoquesMassivamente;
    private final AumentaEstoquesMassivamente aumentaEstoquesMassivamente;
    private final ObtemListaItensComEstoque obtemListaItensComEstoque;

    @Operation(summary = "Realiza um novo cadastro de item", description = "Serviço utilizado para cadastro do item.")
    @PostMapping(produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ItemInsertDTO itemInsertDTO) {
          Item item = cadastraItem.execute(itemInsertDTO);
          return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados do item", description = "Serviço utilizado para alterar os dados do item.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ItemUpdateDTO itemUpdateDTO) {
         var itemRetorno = atualizaItem.execute(id, itemUpdateDTO);
         return new ResponseEntity<>(itemRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o item pelo Id", description = "Serviço utilizado para buscar o item pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var item = obtemItemPeloId.execute(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Operation(summary = "Remove o item pelo Id", description = "Serviço utilizado para remover o item pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
         var item = removeItemPeloId.execute(id);
         return new ResponseEntity<>(new DefaultResponse(Instant.now(),"OK","Item removido."), HttpStatus.OK);
    }

    @Operation(summary = "Aumenta o estoque de um item", description = "Serviço utilizado para aumentar o estoque de um item.")
    @PutMapping(value = "/{id}/aumenta-estoque", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> aumentaEstoque(@PathVariable Long id, @RequestBody EstoqueDTO estoque) {
        var item = aumentaEstoqueItem.execute(id, estoque.quantidade());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Operation(summary = "Consome o estoque de um item", description = "Serviço utilizado para consumir o estoque de um item.")
    @PutMapping(value = "/{id}/consome-estoque", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> consomeEstoque(@PathVariable Long id, @RequestBody EstoqueDTO estoque) {
        var item = consomeEstoqueItem.execute(id, estoque.quantidade());
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Operation(summary = "Consome estoques massivamente", description = "Serviço utilizado para consumir o estoque de varios itens de forma simultanea.")
    @PutMapping(value = "/estoque-massivo/consumo", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> consomeEstoques(@RequestBody List<ItemEstoqueDTO> itemEstoqueList) {
         consomeEstoquesMassivamente.execute(itemEstoqueList);
         return new ResponseEntity<> (new DefaultResponse(Instant.now(),"OK","Estoques consumidos com sucesso."), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Aumenta estoques massivamente", description = "Serviço utilizado para aumentar o estoque de varios itens de forma simultanea.")
    @PutMapping(value = "/estoque-massivo/aumento", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> aumentaEstoques(@RequestBody List<ItemEstoqueDTO> itemEstoqueList) {
        aumentaEstoquesMassivamente.execute(itemEstoqueList);
        return new ResponseEntity<> (new DefaultResponse(Instant.now(),"OK","Estoques foram aumentados com sucesso."), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Obtem todos itens com estoque positivo", description = "Serviço utilizado para obter todos itens com estoque positivo.")
    @GetMapping(value = "/estoque-positivo", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> buscaItensDisponiveis() {
        List<Item> itemList = obtemListaItensComEstoque.execute();
        return new ResponseEntity<> (itemList, HttpStatus.OK);
    }
}
