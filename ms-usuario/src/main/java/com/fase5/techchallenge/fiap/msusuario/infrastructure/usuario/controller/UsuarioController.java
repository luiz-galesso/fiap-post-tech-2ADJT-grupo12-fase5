package com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.controller;

import com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.controller.dto.UsuarioUpdateDTO;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.util.DefaultResponse;
import com.fase5.techchallenge.fiap.msusuario.usecase.usuario.AtualizarUsuario;
import com.fase5.techchallenge.fiap.msusuario.usecase.usuario.ObterUsuarioPeloId;
import com.fase5.techchallenge.fiap.msusuario.usecase.usuario.RemoverUsuarioPeloId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Serviços para manipular os usuarios")
public class UsuarioController {

    private final AtualizarUsuario atualizarUsuario;
    private final ObterUsuarioPeloId obterUsuarioPeloId;
    private final RemoverUsuarioPeloId removerUsuarioPeloId;

    public UsuarioController(AtualizarUsuario atualizarUsuario, ObterUsuarioPeloId obterUsuarioPeloId, RemoverUsuarioPeloId removerUsuarioPeloId) {
        this.atualizarUsuario = atualizarUsuario;
        this.obterUsuarioPeloId = obterUsuarioPeloId;
        this.removerUsuarioPeloId = removerUsuarioPeloId;
    }

    @Operation(summary = "Altera os dados do usuario", description = "Serviço utilizado para alterar os dados do usuario.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        var usuarioRetorno = atualizarUsuario.execute(id, usuarioUpdateDTO);
        return new ResponseEntity<>(usuarioRetorno, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Busca o usuário que está autenticado", description = "Serviço utilizado para buscar os dados do usuário que está autenticado.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public ResponseEntity<?> findById(@PathVariable String id) {
        var usuario = obterUsuarioPeloId.execute(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Remove o usuario pelo Id", description = "Serviço utilizado para remover o usuario pelo Id.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
         removerUsuarioPeloId.execute(id);
         return new ResponseEntity<>(new DefaultResponse(Instant.now(),"Usuario Removido","OK"), HttpStatus.OK);
    }
}
