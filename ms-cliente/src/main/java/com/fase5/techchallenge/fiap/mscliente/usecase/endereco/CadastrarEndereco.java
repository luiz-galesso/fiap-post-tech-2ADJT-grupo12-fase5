package com.fase5.techchallenge.fiap.mscliente.usecase.endereco;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoInsertDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastrarEndereco {

    private final ClienteGateway  clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idCliente, EnderecoInsertDTO enderecoInsertDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Cliente não existe.");
        }

        Endereco endereco =
                new Endereco(null,
                        clienteOptional.get(),
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
