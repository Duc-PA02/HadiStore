package com.example.hadistore.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private Double price;
    private int quantity;
    private int discount;
    private String description;
    private Integer categoryId;
}
