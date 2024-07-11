package com.fase5.techchallenge.fiap.msusuario.usecase.usuario;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarUsuario {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(Usuario usuario) {

        Optional<Usuario> usuarioOptional = usuarioGateway.findById(usuario.getEmail());

        if (usuarioOptional.isPresent()) {
            throw new BussinessErrorException("Já existe um usuario cadastrado com o email informado.");
        }

        return this.usuarioGateway.create(usuario);
    }
}
