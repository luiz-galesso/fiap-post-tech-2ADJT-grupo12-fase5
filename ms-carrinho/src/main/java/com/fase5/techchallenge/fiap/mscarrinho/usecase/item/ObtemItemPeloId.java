package com.fase5.techchallenge.fiap.mscarrinho.usecase.item;

import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.ItemClient;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.ItemResponseDTO;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtemItemPeloId {

    private final ItemClient itemClient;

    public ItemResponseDTO execute(String token, Long idItem) {
        try {
            return this.itemClient.getItem(token, idItem);
        }
        catch( Exception e){
            throw new BussinessErrorException(e.getMessage());
        }
    }
}