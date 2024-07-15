package com.fase5.techchallenge.fiap.mspagamento.usecase.pagamento;

import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.gateway.PagamentoGateway;
import com.fase5.techchallenge.fiap.mspagamento.entity.pagamento.model.Pagamento;
import com.fase5.techchallenge.fiap.mspagamento.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtemPagamentoPeloUsuario {

    private final PagamentoGateway pagamentoGateway;

    public List<Pagamento> execute(String idUsuario) {

        List<Pagamento> pagamentoList = pagamentoGateway.findByUsuario(idUsuario);

        return pagamentoList;
    }
}
