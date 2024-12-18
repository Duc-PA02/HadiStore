package com.example.hadistore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private int quantity;
    private Double price;
    private int discount;
    private String image;
    @Column(length = 1000)
    private String description;
    private LocalDate enteredDate;
    private Boolean status;
    private int sold;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;

    @PrePersist
    private void onCreate(){
        enteredDate = LocalDate.now();
    }
    @PreUpdate
    private void onUpdate(){
        enteredDate = LocalDate.now();
    }
}
