package com.example.hadistore.service.category;

import com.example.hadistore.dtos.CategoryDTO;
import com.example.hadistore.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Integer id, CategoryDTO categoryDTO);
    void deleteCategory(Integer id);
    Category getCategoryById(Integer id);
    List<Category> getAllCategories();
}
