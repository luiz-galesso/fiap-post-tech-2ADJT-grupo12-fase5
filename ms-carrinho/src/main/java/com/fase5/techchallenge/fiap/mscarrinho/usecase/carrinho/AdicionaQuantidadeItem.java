package com.fase5.techchallenge.fiap.mscarrinho.usecase.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.gateway.CarrinhoGateway;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho.Carrinho;
import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdicionaQuantidadeItem {

    private final CarrinhoGateway carrinhoGateway;

    private final CalculaValorTotal calculaValorTotal;

    public Carrinho execute(String id, Long idItem, Long quantidade, double valorUnitario) {

        Optional<Carrinho> optionalCarrinho = carrinhoGateway.findById(id);
        Carrinho carrinho = new Carrinho();
        Boolean existe = false;

        if (optionalCarrinho.isEmpty()) {
            List<Item> itens = new ArrayList<>();
            itens.add(new Item(null, idItem, new Carrinho(id), quantidade, valorUnitario));
            carrinho = new Carrinho(id, itens, valorUnitario * quantidade);
            return carrinhoGateway.create(carrinho);
        } else {
            carrinho = optionalCarrinho.get();
            for (int index = 0; index < carrinho.getItens().size(); index++) {
                if (Objects.equals(carrinho.getItens().get(index).getIdItem(), idItem)) {
                    Item item = carrinho.getItens().get(index);
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    item.setValorUnitario(valorUnitario);
                    carrinho.getItens().set(index, item);
                    existe = true;
                }
            }
            if (!existe) {
                carrinho.getItens().add(new Item(null, idItem, new Carrinho(id), quantidade, valorUnitario));
            }
            carrinho.setValorTotal(calculaValorTotal.execute(carrinho.getItens()));
            return carrinhoGateway.update(carrinho);
        }
    }
}
