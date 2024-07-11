package com.fase5.techchallenge.fiap.msusuario.infrastructure.usuario.repository;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    UserDetails findByEmail(String email);
}
