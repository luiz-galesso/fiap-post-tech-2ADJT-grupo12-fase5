package com.fase5.techchallenge.fiap.mscliente.usecase.endereco;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.controller.dto.EnderecoUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarEndereco {

    private final ClienteGateway  clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idCliente, Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Cliente não existe.");
        }

        Optional<Endereco> enderecoOptional = enderecoGateway.findById(idEndereco);

        if (enderecoOptional.isEmpty()) {
            throw new BussinessErrorException("Endereço não existe.");
        }

        if (!enderecoOptional.get().getCliente().equals(clienteOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao cliente.");
        }

        Endereco endereco =
                new Endereco(enderecoOptional.get().getId(),
                        enderecoOptional.get().getCliente(),
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
