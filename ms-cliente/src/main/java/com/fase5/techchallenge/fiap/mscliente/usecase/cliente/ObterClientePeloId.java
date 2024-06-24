package com.fase5.techchallenge.fiap.mscliente.usecase.cliente;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.gateway.ClienteGateway;
import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.usecase.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterClientePeloId {
    private final ClienteGateway clienteGateway;

    public ObterClientePeloId(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(String id) {
        return this.clienteGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não localizado"));
    }


}