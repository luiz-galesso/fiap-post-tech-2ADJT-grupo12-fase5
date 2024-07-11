package com.fase5.techchallenge.fiap.msusuario.usecase.endereco;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway.UsuarioGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.msusuario.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarEndereco {

    private final UsuarioGateway usuarioGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idUsuario, Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) {

        Optional<Usuario> usuarioOptional = usuarioGateway.findById(idUsuario);

        if (usuarioOptional.isEmpty()) {
            throw new BussinessErrorException("Usuario não existe.");
        }

        Optional<Endereco> enderecoOptional = enderecoGateway.findById(idEndereco);

        if (enderecoOptional.isEmpty()) {
            throw new BussinessErrorException("Endereço não existe.");
        }

        if (!enderecoOptional.get().getUsuario().equals(usuarioOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao usuário.");
        }

        Endereco endereco =
                new Endereco(enderecoOptional.get().getId(),
                        enderecoOptional.get().getUsuario(),
                        enderecoUpdateDTO.getLogradouro(),
                        enderecoUpdateDTO.getNumero(),
                        enderecoUpdateDTO.getBairro(),
                        enderecoUpdateDTO.getComplemento(),
                        enderecoUpdateDTO.getCep(),
                        enderecoUpdateDTO.getCidade(),
                        enderecoUpdateDTO.getEstado(),
                        enderecoUpdateDTO.getReferencia()
                );

        return this.enderecoGateway.update(endereco);
    }
}
