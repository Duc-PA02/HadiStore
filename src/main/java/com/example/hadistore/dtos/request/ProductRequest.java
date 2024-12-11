package com.example.hadistore.dtos.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private Double price;
    private int quantity;
    private int discount;
    private String image;
    @Size(max = 1000, message = "Description must not exceed 1000 characters.")
    private String description;
    private Long categoryId;
}
