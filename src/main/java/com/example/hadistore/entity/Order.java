package com.example.hadistore.entity;

import com.example.hadistore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "total_amount")
    @PositiveOrZero
    private BigDecimal totalAmount;
    @Column(name = "shipping_cost")
    @PositiveOrZero
    private BigDecimal shippingCost;
    @Column(name = "shipping_date")
    private LocalDate shippingDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", foreignKey = @ForeignKey(name = "fk_order_coupon"))
    @JsonBackReference
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "fk_order_payment"))
    @JsonBackReference
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_order_user"))
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "order")
    private Cart cart;

    public Order() {
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }
}
