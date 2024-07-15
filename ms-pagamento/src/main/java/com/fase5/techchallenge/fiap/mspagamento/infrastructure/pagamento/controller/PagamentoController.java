package com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.controller;

import com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.controller.dto.PagamentoRequestDTO;
import com.fase5.techchallenge.fiap.mspagamento.security.TokenService;
import com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento.ObtemPagamentoPeloId;
import com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento.ObtemPagamentoPeloUsuario;
import com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento.RealizaPagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@AllArgsConstructor
@Tag(name = "Pagamentos", description = "Serviços para manipular os pagamentos")
public class PagamentoController {

    private final ObtemPagamentoPeloId obtemPagamentoPeloId;
    private final RealizaPagamento realizaPagamento;
    private final TokenService tokenService;
    private final ObtemPagamentoPeloUsuario obtemPagamentoPeloUsuario;

    @Operation(summary = "Busca o pagamento pelo Id", description = "Serviço utilizado para buscar o pagamento pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var pagamento = obtemPagamentoPeloId.execute(id);
        return new ResponseEntity<>(pagamento, HttpStatus.OK);
    }

    @Operation(summary = "Busca o pagamento pelo Id", description = "Serviço utilizado para buscar o pagamento pelo Id.")
    @PostMapping(produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> realizaPagamento(HttpServletRequest request, @RequestBody PagamentoRequestDTO pagamentoRequestDTO) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var pagamento = realizaPagamento.execute(pagamentoRequestDTO.valor(), idUsuario);
        return new ResponseEntity<>(pagamento, HttpStatus.OK);
    }

    @Operation(summary = "Busca o pagamento pelo usuário", description = "Serviço utilizado para buscar o pagamento pelo usuario.")
    @GetMapping(produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> obtemPagamentoPeloUsuario(HttpServletRequest request) {
        String idUsuario = tokenService.getUserIdFromToken(request);
        var pagamento = obtemPagamentoPeloUsuario.execute(idUsuario);
        return new ResponseEntity<>(pagamento, HttpStatus.OK);
    }
}
