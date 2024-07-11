package com.fase5.techchallenge.fiap.msusuario.usecase.endereco;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ObterEnderecosPeloUsuario {

    private final UsuarioGateway usuarioGateway;
    private final EnderecoGateway enderecoGateway;

    public List<Endereco> execute(String idUsuario) {
        Optional<Usuario> usuarioOptional = usuarioGateway.findById(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Usuario não existe.");
        }
        return this.enderecoGateway.findByUsuario(usuarioOptional.get());
    }
}
