package com.fase5.techchallenge.fiap.msusuario.usecase.usuario;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterUsuarioPeloId {
    private final UsuarioGateway usuarioGateway;

    public ObterUsuarioPeloId(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario execute(String id) {
        return this.usuarioGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não localizado"));
    }


}