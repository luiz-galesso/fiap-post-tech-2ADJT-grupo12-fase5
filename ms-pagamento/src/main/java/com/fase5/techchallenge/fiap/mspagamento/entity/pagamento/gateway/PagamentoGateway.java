package com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.gateway;

import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model.Pagamento;
import com.fase5.techchallenge.fiap.mspagamento.infrastructure.pagamento.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PagamentoGateway {

    private PagamentoRepository pagamentoRepository;

    public Optional<Pagamento> findById(Long id) {
        return this.pagamentoRepository.findById(id);
    }

    public Pagamento create(Pagamento pagamento) {
        return this.pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> findByUsuario(String idUsuario) {
        return pagamentoRepository.findByIdUsuario(idUsuario);
    }
}