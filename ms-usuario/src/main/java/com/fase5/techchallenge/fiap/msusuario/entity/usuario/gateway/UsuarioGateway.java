package com.fase5.techchallenge.fiap.msusuario.entity.usuario.gateway;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class UsuarioGateway {

    private UsuarioRepository usuarioRepository;

    public UsuarioGateway(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario create(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(String email) {
        return this.usuarioRepository.findById(email);
    }

    public void remove(String email) {
        usuarioRepository.deleteById(email);
    }

}