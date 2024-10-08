package com.example.hadistore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<Product> productList;
}
