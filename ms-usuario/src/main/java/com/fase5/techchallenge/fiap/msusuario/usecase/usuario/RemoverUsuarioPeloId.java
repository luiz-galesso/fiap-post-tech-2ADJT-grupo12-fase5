package com.fase5.techchallenge.fiap.msusuario.usecase.usuario;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.usecase.endereco.ObterEnderecosPeloUsuario;
import com.fase5.techchallenge.fiap.msusuario.usecase.endereco.RemoverEnderecoPeloId;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoverUsuarioPeloId {
    private final UsuarioGateway usuarioGateway;

    private final ObterEnderecosPeloUsuario obterEnderecosPeloUsuario;

    private final RemoverEnderecoPeloId removerEnderecoPeloId;

    public void execute(String id) {
        Optional<Usuario> usuarioOptional = usuarioGateway.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o usuario cadastrado com o email informado.");
        }

        List<Endereco> enderecoList = obterEnderecosPeloUsuario.execute(usuarioOptional.get().getEmail());
        enderecoList.forEach( e -> removerEnderecoPeloId.execute(usuarioOptional.get().getEmail(), e.getId()));

        usuarioGateway.remove(id);
    }

}