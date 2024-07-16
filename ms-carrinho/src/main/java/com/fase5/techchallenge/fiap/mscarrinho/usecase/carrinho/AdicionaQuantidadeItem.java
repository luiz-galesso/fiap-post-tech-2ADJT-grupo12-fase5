package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway.CarrinhoGateway;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import com.fase5.techchallenge.fiap.mscarrinho.infrastructure.feign.dto.ItemResponseDTO;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.exception.BussinessErrorException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fase5.techchallenge.fiap.mscarrinho.usecase.item.ObtemItemPeloId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdicionaQuantidadeItem {

    private final CarrinhoGateway carrinhoGateway;

    private final CalculaValorTotal calculaValorTotal;
    private final ObtemItemPeloId obtemItemPeloId;

    public Carrinho execute(String token, String id, Long idItem, Long quantidade) {
        ItemResponseDTO itemNovo = new ItemResponseDTO();
        Optional<Carrinho> optionalCarrinho = carrinhoGateway.findById(id);
        Carrinho carrinho = new Carrinho();
        Boolean existe = false;
        try {
            itemNovo = obtemItemPeloId.execute(token, idItem);
        } catch (Exception ex) {
            throw new BussinessErrorException("Item não localizado");
        }

        if (optionalCarrinho.isEmpty()) {
            List<Item> itens = new ArrayList<>();
            itens.add(new Item(null, idItem, new Carrinho(id), quantidade, itemNovo.getValorUnitario()));
            carrinho = new Carrinho(id, itens, itemNovo.getValorUnitario() * quantidade);
            return carrinhoGateway.create(carrinho);
        } else {
            carrinho = optionalCarrinho.get();
            for (int index = 0; index < carrinho.getItens().size(); index++) {
                if (Objects.equals(carrinho.getItens().get(index).getIdItem(), idItem)) {
                    Item item = carrinho.getItens().get(index);
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    item.setValorUnitario(item.getValorUnitario());
                    carrinho.getItens().set(index, item);
                    existe = true;
                }
            }
            if (!existe) {
                carrinho.getItens().add(new Item(null, idItem, new Carrinho(id), quantidade, itemNovo.getValorUnitario()));
            }
            carrinho.setValorTotal(calculaValorTotal.execute(carrinho.getItens()));
            return carrinhoGateway.update(carrinho);
        }
    }
}
