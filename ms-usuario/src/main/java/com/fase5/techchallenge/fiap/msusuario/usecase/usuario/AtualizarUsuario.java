package com.fase5.techchallenge.fiap.msusuario.usecase.usuario;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.controller.dto.UsuarioUpdateDTO;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarUsuario {

    private final UsuarioGateway usuarioGateway;

    public Usuario execute(String email, UsuarioUpdateDTO usuarioUpdateDTO) {

        Optional<Usuario> usuarioOptional = usuarioGateway.findById(email);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o usuario cadastrado com o email informado.");
        }

        /*Usuario usuario = new Usuario(email,
                usuarioUpdateDTO.getNome(),
                usuarioOptional.get().getDataRegistro(),
                usuarioOptional.getDataNascimento()
        );*/

        return null;//this.usuarioGateway.update(usuario);
    }
}
