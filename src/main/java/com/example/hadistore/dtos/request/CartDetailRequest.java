package com.example.hadistore.dtos.request;

import lombok.Getter;

@Getter
public class CartDetailRequest {
    private Long cartDetailId;
    private int quantity;
    private Double price;
}
