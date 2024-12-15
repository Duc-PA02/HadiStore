package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_detail")
@Getter
@Setter
public class CartDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartDetailId;
    private int quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "cart_id", foreignKey = @ForeignKey(name = "fk_cart_item_cart"))
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_cart_item_product"))
    private Product product;
}
