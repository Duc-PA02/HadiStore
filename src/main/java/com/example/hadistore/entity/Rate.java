package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "rate")
@Getter
@Setter
@NoArgsConstructor
public class Rate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String comment;
    private Date rateDate;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_rate_user"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_rate_product"))
    private Product product;

    @OneToOne
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    public Rate(Double rating, String comment, Date rateDate, User user, Product product, OrderDetail orderDetail) {
        this.rating = rating;
        this.comment = comment;
        this.rateDate = rateDate;
        this.user = user;
        this.product = product;
        this.orderDetail = orderDetail;
    }
}
