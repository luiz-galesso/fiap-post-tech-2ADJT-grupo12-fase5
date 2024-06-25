package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.AdicionaQuantideItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.RemoveItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.RemoveQuantidadeItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.util.DefaultResponse;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
@Tag(name = "Carrinho", description = "Serviços para manipular o carrinho de compras")
public class CarrinhoController {

    private final ObtemCarrinhoPeloId obtemCarrinhoPeloId;

    private final AdicionaQuantidadeItem adicionaQuantidadeItem;

    private final RemoveQuantidadeItem removeQuantidadeItem;

    private final RemoveItem removeItem;

    private final LimpaCarrinho limpaCarrinho;

    @Operation(summary = "Busca o carrinho pelo id do usuário", description = "Serviço utilizado para buscar o carrinho pelo id do usuário.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String id) {
        var carrinho = obtemCarrinhoPeloId.execute(id);
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Insere a quantidade informada do item no carrinho", description = "Serviço utilizado para inserir a quantidade informada do item no carrinho.")
    @PutMapping(value = "/{id}/inclusao-quantidade-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> adicionaQuantidadeItem(@PathVariable String id, @RequestBody AdicionaQuantideItemDTO adicionaQuantidadeItemDTO) {
        var carrinho = adicionaQuantidadeItem.execute(id, adicionaQuantidadeItemDTO.idItem(), adicionaQuantidadeItemDTO.quantidade(), adicionaQuantidadeItemDTO.valorUnitario());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Remove a quantidade de um item do carrinho", description = "Serviço utilizado para remover a quantidade de um item do carrinho.")
    @PutMapping(value = "/{id}/remocao-quantidade-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> removeItem(@PathVariable String id, @RequestBody RemoveQuantidadeItemDTO removeQuantidadeItemDTO) {
        var carrinho = removeQuantidadeItem.execute(id, removeQuantidadeItemDTO.idItem(), removeQuantidadeItemDTO.quantidade());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }
    @Operation(summary = "Remove o item do carrinho", description = "Serviço utilizado para remover um item do carrinho.")
    @PutMapping(value = "/{id}/remocao-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> removeItem(@PathVariable String id, @RequestBody RemoveItemDTO removeItemDTO) {
        var carrinho = removeItem.execute(id, removeItemDTO.idItem());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Limpa o carrinho", description = "Serviço utilizado para limpar o carrinho.")
    @DeleteMapping(value = "/{id}/limpeza", produces = "application/json")
    @Transactional
    public ResponseEntity<?> limpaCarrinho(@PathVariable String id) {
        limpaCarrinho.execute(id);
        return new ResponseEntity<>(new DefaultResponse(Instant.now(),"OK","Carrinho limpo."), HttpStatus.OK);
    }
}
