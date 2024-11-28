package com.example.hadistore.dtos.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private int sale;
    private Integer quantity;
    private Integer categoryId;
}
