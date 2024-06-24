package com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller;

import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.endereco.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
@Tag(name = "Enderecos", description = "Serviços para manipular os endereços de um cliente")
public class EnderecoController {

    private final CadastrarEndereco cadastrarEndereco;
    private final AtualizarEndereco atualizarEndereco;
    private final ObterEnderecoPeloId obterEnderecoPeloId;
    private final ObterEnderecosPeloCliente obterEnderecosPeloCliente;
    private final RemoverEnderecoPeloId removerEnderecoPeloId;

    @Operation(summary = "Realiza o cadastro de um novo endereço", description = "Serviço utilizado para realizar o cadastro de um novo endereço do cliente.")
    @PostMapping(value = "/{idCliente}/enderecos", produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@PathVariable String idCliente, @RequestBody EnderecoInsertDTO enderecoInsertDTO) {
        Endereco endereco = cadastrarEndereco.execute(idCliente, enderecoInsertDTO);
        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera o endereço do cliente", description = "Serviço utilizado para alterar um endereço do cliente.")
    @PutMapping(value = "/{idCliente}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String idCliente, @PathVariable Integer idEndereco, @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) {
        Endereco endereco = atualizarEndereco.execute(idCliente, idEndereco, enderecoUpdateDTO);
        return new ResponseEntity<>(endereco, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o endereço do clie pelo pelo id do cliente e id do endereço", description = "Serviço utilizado para buscar o endereço do cliente pelo id do cliente e pelo id do endereço.")
    @GetMapping(value = "/{idCliente}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String idCliente, @PathVariable Integer idEndereco) {
        var cliente = obterEnderecoPeloId.execute(idCliente, idEndereco);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos endereços de um cliente", description = "Serviço utilizado para buscar todos endereços de um cliente pelo id do cliente.")
    @GetMapping(value = "/{idCliente}/enderecos", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findByCliente(@PathVariable String idCliente) {
        List<Endereco> enderecoList = obterEnderecosPeloCliente.execute(idCliente);
        return new ResponseEntity<>(enderecoList, HttpStatus.OK);
    }

    @Operation(summary = "Remove o endereco do cliente pelo id endereço", description = "Serviço utilizado para remover o endereço de um cliente pelo Id endereço.")
    @DeleteMapping(value = "/{idCliente}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String idCliente, Integer idEndereco) {
        removerEnderecoPeloId.execute(idCliente, idEndereco);
        return new ResponseEntity<>("Endereço removido com sucesso.", HttpStatus. OK);
    }

}
