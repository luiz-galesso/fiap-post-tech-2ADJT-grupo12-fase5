package com.fase5.techchallenge.fiap.mscarrinho.usecase.pagamento;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.PagamentoClient;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.PagamentoDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.PagamentoResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.exception.BussinessErrorException;

@Service
@AllArgsConstructor
public class RealizaPagamento {

    private final PagamentoClient pagamentoClient;

    public PagamentoResponseDTO execute(String token, PagamentoDTO pagamentoDTO) {
        try {
            return this.pagamentoClient.pagamento(token, pagamentoDTO);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}