package com.example.hadistore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryBestSellerResponse {
    private String name;
    private int count;
    private Double amount;
}
