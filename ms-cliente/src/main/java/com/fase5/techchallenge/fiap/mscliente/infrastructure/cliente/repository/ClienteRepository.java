package com.fase5.techchallenge.fiap.mscliente.infrastructure.cliente.repository;

import com.fase5.techchallenge.fiap.mscliente.entity.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
