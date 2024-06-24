package com.fase5.techchallenge.fiap.mscliente.usecase.endereco;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway.EnderecoGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ObterEnderecoPeloId {
    
    private final ClienteGateway clienteGateway;
    private final EnderecoGateway enderecoGateway;

    public Endereco execute(String idCliente, Integer idEndereco) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Cliente não existe.");
        }

        Endereco endereco = this.enderecoGateway.findById(idEndereco).orElseThrow(() -> new EntityNotFoundException("Endereço não localizado"));

        if (!endereco.getCliente().equals(clienteOptional.get())) {
            throw new BussinessErrorException("Endereço não pertence ao cliente.");
        }
        return endereco;
    }
}
