package com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento;

import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.gateway.PagamentoGateway;
import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model.Pagamento;
import com.fase5.techchallenge.fiap.mspagamento.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtemPagamentoPeloId {

    private final PagamentoGateway pagamentoGateway;

    public Pagamento execute(Long id) {

        Pagamento pagamento = pagamentoGateway.findById(id).orElseThrow(() -> new BussinessErrorException("Não foi encontrado o pagamento cadastrado com o id informado."));

        return pagamento;
    }
}
