package com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.controller;

import com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento.ObtemPagamentoPeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@AllArgsConstructor
@Tag(name = "Pagamentos", description = "Serviços para manipular os pagamentos")
public class PagamentoController {

    private final ObtemPagamentoPeloId obtemPagamentoPeloId;

    @Operation(summary = "Busca o pagamento pelo Id", description = "Serviço utilizado para buscar o pagamento pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable Long id) {
        var pagamento = obtemPagamentoPeloId.execute(id);
        return new ResponseEntity<>(pagamento, HttpStatus.OK);
    }
}
