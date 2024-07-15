package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.PagamentoDTO;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.PagamentoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "pagamentos", url = "${feign.ms-pagamento.url}")
public interface PagamentoClient {

    @RequestMapping(method = RequestMethod.POST, value = "/pagamentos")
    PagamentoResponseDTO pagamento(@RequestHeader("Authorization") String token, @RequestBody PagamentoDTO pagamentoDTO);

}