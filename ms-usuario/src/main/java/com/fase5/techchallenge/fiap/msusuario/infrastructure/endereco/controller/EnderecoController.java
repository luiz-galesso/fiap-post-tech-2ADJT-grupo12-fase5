package com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.controller;

import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.msusuario.usecase.endereco.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@Tag(name = "Enderecos", description = "Serviços para manipular os endereços de um usuario")
public class EnderecoController {

    private final CadastrarEndereco cadastrarEndereco;
    private final AtualizarEndereco atualizarEndereco;
    private final ObterEnderecoPeloId obterEnderecoPeloId;
    private final ObterEnderecosPeloUsuario obterEnderecosPeloUsuario;
    private final RemoverEnderecoPeloId removerEnderecoPeloId;

    @Operation(summary = "Realiza o cadastro de um novo endereço", description = "Serviço utilizado para realizar o cadastro de um novo endereço do usuario.")
    @PostMapping(value = "/{idUsuario}/enderecos", produces = "application/json")
    @Transactional
    public ResponseEntity<?> create(@PathVariable String idUsuario, @RequestBody EnderecoInsertDTO enderecoInsertDTO) {
        Endereco endereco = cadastrarEndereco.execute(idUsuario, enderecoInsertDTO);
        return new ResponseEntity<>(endereco, HttpStatus.CREATED);
    }

    @Operation(summary = "Altera o endereço do usuario", description = "Serviço utilizado para alterar um endereço do usuario.")
    @PutMapping(value = "/{idUsuario}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String idUsuario, @PathVariable Integer idEndereco, @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) {
        Endereco endereco = atualizarEndereco.execute(idUsuario, idEndereco, enderecoUpdateDTO);
        return new ResponseEntity<>(endereco, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o endereço do clie pelo pelo id do usuario e id do endereço", description = "Serviço utilizado para buscar o endereço do usuario pelo id do usuario e pelo id do endereço.")
    @GetMapping(value = "/{idUsuario}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String idUsuario, @PathVariable Integer idEndereco) {
        var usuario = obterEnderecoPeloId.execute(idUsuario, idEndereco);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos endereços de um usuario", description = "Serviço utilizado para buscar todos endereços de um usuario pelo id do usuario.")
    @GetMapping(value = "/{idUsuario}/enderecos", produces = "application/json")
    @Transactional
    public ResponseEntity<?> findByUsuario(@PathVariable String idUsuario) {
        List<Endereco> enderecoList = obterEnderecosPeloUsuario.execute(idUsuario);
        return new ResponseEntity<>(enderecoList, HttpStatus.OK);
    }

    @Operation(summary = "Remove o endereco do usuario pelo id endereço", description = "Serviço utilizado para remover o endereço de um usuario pelo Id endereço.")
    @DeleteMapping(value = "/{idUsuario}/enderecos/{idEndereco}", produces = "application/json")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String idUsuario, Integer idEndereco) {
        removerEnderecoPeloId.execute(idUsuario, idEndereco);
        return new ResponseEntity<>("Endereço removido com sucesso.", HttpStatus. OK);
    }

}
