package com.example.hadistore.service;

import com.example.hadistore.dtos.request.CategoryRequest;
import com.example.hadistore.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);

    Category updateCategory(Integer id, CategoryRequest categoryRequest);

    void deleteCategory(Integer id);

    Category getCategoryById(Integer id);

    List<Category> getAllCategories();
}
