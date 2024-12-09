package com.example.hadistore.dtos.request;

import lombok.Getter;

@Getter
public class CartRequest {
    private Double amount;
    private String address;
    private String phone;
}
