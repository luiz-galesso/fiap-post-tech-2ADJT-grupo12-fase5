package com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento;

import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.gateway.PagamentoGateway;
import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model.Pagamento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RealizaPagamento {

    private final PagamentoGateway pagamentoGateway;

    public Pagamento execute(Double valor, String idUsuario) {

        Pagamento pagamento = new Pagamento(null, valor, idUsuario, "APROVADO", LocalDateTime.now());
        pagamentoGateway.create(pagamento);

        return pagamento;
    }
}
