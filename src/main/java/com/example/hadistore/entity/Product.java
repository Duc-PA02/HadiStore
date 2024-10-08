package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    @PositiveOrZero
    private BigDecimal price;
    @Column(name = "sale")
    private Integer sale;
    @Column(name = "hot")
    private Boolean hot;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "total_rate")
    private Integer totalRate;
    @Column(name = "total_start")
    private Float totalStart;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "color")
    private String color;
    @Column(name = "size")
    private Integer size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_product_category"))
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Rate> rateList;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductImage> productImageList;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<CartItem> cartItemList;
}
