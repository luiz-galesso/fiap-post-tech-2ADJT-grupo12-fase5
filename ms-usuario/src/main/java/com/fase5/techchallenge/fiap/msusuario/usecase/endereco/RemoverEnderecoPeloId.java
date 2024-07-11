package com.fase5.techchallenge.fiap.msusuario.usecase.endereco;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoverEnderecoPeloId {
    private final UsuarioGateway usuarioGateway;
    private final EnderecoGateway enderecoGateway;

    public void execute(String idUsuario, Integer idEndereco) {
        Optional<Usuario> usuarioOptional = usuarioGateway.findById(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o usuario cadastrado com o email informado.");
        }

        Optional<Endereco> enderecoOptional = enderecoGateway.findById(idEndereco);

        if (enderecoOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o endereco o id informado.");
        }

        if (!enderecoOptional.get().getUsuario().equals(usuarioOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao usuario.");
        }

        enderecoGateway.remove(idEndereco);
    }
}
