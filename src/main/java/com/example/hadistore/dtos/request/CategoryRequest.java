package com.example.hadistore.dtos.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "category's name cannot be empty")
    private String categoryName;
}
