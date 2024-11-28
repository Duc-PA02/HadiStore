package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "rate")
@Getter
@Setter
public class Rate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String comment;
    private Date rateDate;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_rate_user"))
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_rate_product"))
    @JsonBackReference
    private Product product;

    @OneToOne
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;
}
