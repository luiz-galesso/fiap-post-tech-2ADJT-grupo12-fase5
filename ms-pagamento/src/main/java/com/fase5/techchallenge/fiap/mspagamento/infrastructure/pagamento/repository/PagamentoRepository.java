package com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.repository;

import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByIdUsuario(String idUsuario);
}
