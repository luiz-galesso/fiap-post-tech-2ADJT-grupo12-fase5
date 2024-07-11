package com.fase5.techchallenge.fiap.msusuario.usecase.endereco;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ObterEnderecoPeloId {
    
    private final UsuarioGateway usuarioGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idUsuario, Integer idEndereco) {

        Optional<Usuario> usuarioOptional = usuarioGateway.findById(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Usuario não existe.");
        }

        Endereco endereco = this.enderecoGateway.findById(idEndereco).orElseThrow(() -> new EntityNotFoundException("Endereço não localizado"));

        if (!endereco.getUsuario().equals(usuarioOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao usuario.");
        }
        return endereco;
    }
}
