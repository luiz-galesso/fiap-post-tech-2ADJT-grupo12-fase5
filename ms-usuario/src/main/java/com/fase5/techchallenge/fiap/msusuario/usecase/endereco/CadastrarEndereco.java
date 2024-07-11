package com.fase5.techchallenge.fiap.msusuario.usecase.endereco;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarEndereco {

    private final UsuarioGateway usuarioGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idUsuario, EnderecoInsertDTO enderecoInsertDTO) {

        Optional<Usuario> usuarioOptional = usuarioGateway.findById(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Usuario não existe.");
        }

        Endereco endereco =
                new Endereco(null,
                        usuarioOptional.get(),
                        enderecoInsertDTO.getLogradouro(),
                        enderecoInsertDTO.getNumero(),
                        enderecoInsertDTO.getBairro(),
                        enderecoInsertDTO.getComplemento(),
                        enderecoInsertDTO.getCep(),
                        enderecoInsertDTO.getCidade(),
                        enderecoInsertDTO.getEstado(),
                        enderecoInsertDTO.getReferencia()
                );
        return this.enderecoGateway.create(endereco);
    }
}
