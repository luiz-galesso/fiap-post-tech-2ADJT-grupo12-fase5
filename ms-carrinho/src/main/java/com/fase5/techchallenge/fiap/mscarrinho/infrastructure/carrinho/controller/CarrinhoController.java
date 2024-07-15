package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.AdicionaQuantideItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.RealizaPagamentoDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.RemoveItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.carrinho.controller.dto.RemoveQuantidadeItemDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.PagamentoClient;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.PagamentoDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.util.DefaultResponse;
import com.fase5.techchallenge.fiap.mscarrinho.security.TokenService;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho.*;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.pagamento.RealizaPagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    private final TokenService tokenService;

    private final RealizaPagamento realizaPagamento;

    @Operation(summary = "Busca o carrinho pelo id do usuário", description = "Serviço utilizado para buscar o carrinho pelo id do usuário.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(HttpServletRequest request) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var carrinho = obtemCarrinhoPeloId.execute(idUsuario);
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Insere a quantidade informada do item no carrinho", description = "Serviço utilizado para inserir a quantidade informada do item no carrinho.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/inclusao-quantidade-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> adicionaQuantidadeItem(HttpServletRequest request, @RequestBody AdicionaQuantideItemDTO adicionaQuantidadeItemDTO) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var carrinho = adicionaQuantidadeItem.execute(idUsuario, adicionaQuantidadeItemDTO.idItem(), adicionaQuantidadeItemDTO.quantidade(), adicionaQuantidadeItemDTO.valorUnitario());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Remove a quantidade de um item do carrinho", description = "Serviço utilizado para remover a quantidade de um item do carrinho.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}/remocao-quantidade-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> removeItem(HttpServletRequest request, @RequestBody RemoveQuantidadeItemDTO removeQuantidadeItemDTO) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var carrinho = removeQuantidadeItem.execute(idUsuario, removeQuantidadeItemDTO.idItem(), removeQuantidadeItemDTO.quantidade());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }
    @Operation(summary = "Remove o item do carrinho", description = "Serviço utilizado para remover um item do carrinho.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}/remocao-item", produces = "application/json")
    @Transactional
    public ResponseEntity<?> removeItem(HttpServletRequest request, @RequestBody RemoveItemDTO removeItemDTO) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var carrinho = removeItem.execute(idUsuario, removeItemDTO.idItem());
        return new ResponseEntity<>(carrinho, HttpStatus.OK);
    }

    @Operation(summary = "Limpa o carrinho", description = "Serviço utilizado para limpar o carrinho.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/limpeza", produces = "application/json")
    @Transactional
    public ResponseEntity<?> limpaCarrinho(HttpServletRequest request) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        limpaCarrinho.execute(idUsuario);
        return new ResponseEntity<>(new DefaultResponse(Instant.now(),"OK","Carrinho limpo."), HttpStatus.OK);
    }

    @Operation(summary = "Pagamento de um carrinho de compras", description = "Serviço utilizado para realizar o pagamento de um carrinho de compras.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/pagamento", produces = "application/json")
    @Transactional
    public ResponseEntity<?> pagamento(HttpServletRequest request, @RequestBody RealizaPagamentoDTO realizaPagamentoDTO) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var carrinho = obtemCarrinhoPeloId.execute(idUsuario);
        try {
            realizaPagamento.execute(request.getHeader("Authorization"), new PagamentoDTO(carrinho.getValorTotal(), realizaPagamentoDTO.numeroCartao(), realizaPagamentoDTO.validadeCartao(),realizaPagamentoDTO.nomeCartao(),realizaPagamentoDTO.cvvCartao()));
        } catch(Exception ex)
        {
            return new ResponseEntity<>(new DefaultResponse(Instant.now(),"KO","Não foi possível realizar o pagamento."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        limpaCarrinho.execute(idUsuario);
        return new ResponseEntity<>(new DefaultResponse(Instant.now(),"OK","Pagamento Efetuado com sucesso."), HttpStatus.OK);
    }
}
