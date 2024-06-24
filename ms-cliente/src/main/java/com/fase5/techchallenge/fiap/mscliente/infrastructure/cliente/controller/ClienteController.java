package com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.util.DefaultResponse;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.AtualizarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.CadastrarCliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.ObterClientePeloId;
import com.fase5.techchallenge.fiap.mscliente.usecase.cliente.RemoverClientePeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Serviços para manipular os clientes")
public class ClienteController {

    private final CadastrarCliente cadastrarCliente;
    private final AtualizarCliente atualizarCliente;
    private final ObterClientePeloId obterClientePeloId;
    private final RemoverClientePeloId removerClientePeloId;

    public ClienteController(CadastrarCliente cadastrarCliente, AtualizarCliente atualizarCliente, ObterClientePeloId obterClientePeloId, RemoverClientePeloId removerClientePeloId) {
        this.cadastrarCliente = cadastrarCliente;
        this.atualizarCliente = atualizarCliente;
        this.obterClientePeloId = obterClientePeloId;
        this.removerClientePeloId = removerClientePeloId;
    }

    @Operation(summary = "Realiza um novo cadastro de cliente", description = "Serviço utilizado para cadastro do cliente.")
    @PostMapping(produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@RequestBody ClienteInsertDTO clienteInsertDTO) {
        Cliente cliente = cadastrarCliente.execute(clienteInsertDTO);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados do cliente", description = "Serviço utilizado para alterar os dados do cliente.")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        var clienteRetorno = atualizarCliente.execute(id, clienteUpdateDTO);
        return new ResponseEntity<>(clienteRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o cliente pelo Id", description = "Serviço utilizado para buscar o cliente pelo Id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String id) {
        var cliente = obterClientePeloId.execute(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Remove o cliente pelo Id", description = "Serviço utilizado para remover o cliente pelo Id.")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
         removerClientePeloId.execute(id);
         return new ResponseEntity<>(new DefaultResponse(Instant.now(),"Cliente Removido","OK"), HttpStatus.OK);
    }
}
