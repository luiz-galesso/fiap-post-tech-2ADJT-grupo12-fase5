package com.fase5.techchallenge.fiap.msusuario.infrastructure.endereco.repository;

import com.fase5.techchallenge.fiap.msusuario.entity.usuario.model.Usuario;
import com.fase5.techchallenge.fiap.msusuario.entity.endereco.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByUsuario(Usuario usuario);

}
