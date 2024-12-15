package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Double amount;
    private String address;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_cart_user"))
    private User user;

    public Cart(double amount, String address, String phone, User user) {
        this.amount = amount;
        this.address = address;
        this.phone = phone;
        this.user = user;
    }
}
