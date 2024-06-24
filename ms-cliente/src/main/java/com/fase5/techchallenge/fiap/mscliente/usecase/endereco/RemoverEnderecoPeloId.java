package com.fase5.techchallenge.fiap.mscliente.usecase.endereco;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoverEnderecoPeloId {
    private final ClienteGateway clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public void execute(String idCliente, Integer idEndereco) {
        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        Optional<Endereco> enderecoOptional = enderecoGateway.findById(idEndereco);

        if (enderecoOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o endereco o id informado.");
        }

        if (!enderecoOptional.get().getCliente().equals(clienteOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao cliente.");
        }

        enderecoGateway.remove(idEndereco);
    }
}
