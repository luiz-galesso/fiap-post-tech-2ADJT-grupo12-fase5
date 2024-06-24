package com.fase5.techchallenge.fiap.mscliente.entity.endereco.gateway;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import com.fase5.techchallenge.fiap.mscliente.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.mscliente.infrastructure.endereco.repository.EnderecoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EnderecoGateway {

    private final EnderecoRepository enderecoRepository;

    public EnderecoGateway(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco create(Endereco endereco) {
        return this.enderecoRepository.save(endereco);
    }

    public Endereco update(Endereco endereco) {
        return this.enderecoRepository.save(endereco);
    }

    public Optional<Endereco> findById(Integer idEndereco) {
        return this.enderecoRepository.findById(idEndereco);
    }

    public List<Endereco> findByCliente(Cliente cliente) {
        return this.enderecoRepository.findByCliente(cliente);
    }
    public void remove(Integer id) {
        enderecoRepository.deleteById(id);
    }

}