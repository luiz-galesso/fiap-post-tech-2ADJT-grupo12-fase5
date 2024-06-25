package com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.carrinho;

import com.fase5.techchallenge.fiap.mscarrinho.entity.carrinho.model.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "tb_carrinho")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carrinho {

    @Id
    private String id;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private List<Item> itens;

    private double valorTotal;


    public Carrinho (String id) {
        this.id = id;
    }
}
