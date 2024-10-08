package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "coupon")
@Setter
@Getter
public class Coupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 10)
    private String code;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "description")
    private String description;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "usage_limit")
    private Integer usageLimit = 1;
    @Column(name = "min_amount")
    @Positive
    private BigDecimal minAmount = BigDecimal.ZERO;
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "coupon")
    @JsonManagedReference
    private List<Order> orderList;
}
