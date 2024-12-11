package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CategoryRequest;
import com.example.hadistore.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);

    Category updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();
}
