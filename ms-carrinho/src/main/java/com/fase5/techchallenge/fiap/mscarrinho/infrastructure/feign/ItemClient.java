package com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.ItemResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "item", url = "${feign.ms-item.url}")
public interface ItemClient {

    @RequestMapping(method = RequestMethod.GET, value = "/itens/{idItem}")
    ItemResponseDTO getItem(@RequestHeader("Authorization") String token, @PathVariable Long idItem);

}