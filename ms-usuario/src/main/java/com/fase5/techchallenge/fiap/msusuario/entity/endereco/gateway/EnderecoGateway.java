package com.fase5.techchallenge.fiap.msusuario.entity.endereco.gateway;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.repository.EnderecoRepository;
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

    public List<Endereco> findByUsuario(Usuario usuario) {
        return this.enderecoRepository.findByUsuario(usuario);
    }
    public void remove(Integer id) {
        enderecoRepository.deleteById(id);
    }

}