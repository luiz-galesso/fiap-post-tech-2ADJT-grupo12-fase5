package com.fase5.techchallenge.fiap.mscliente.usecase.cliente;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.controller.dto.ClienteUpdateDTO;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarCliente {

    private final ClienteGateway clienteGateway;

    public Cliente execute(String email, ClienteUpdateDTO clienteUpdateDTO) {

        Optional<Cliente> clienteOptional = clienteGateway.findById(email);

        if (clienteOptional.isEmpty()) {
            throw new BussinessErrorException("Não foi encontrado o cliente cadastrado com o email informado.");
        }

        Cliente cliente = new Cliente(email,
                clienteUpdateDTO.getNome(),
                clienteOptional.get().getDataRegistro(),
                clienteUpdateDTO.getDataNascimento()
        );
        return this.clienteGateway.update(cliente);
    }
}
