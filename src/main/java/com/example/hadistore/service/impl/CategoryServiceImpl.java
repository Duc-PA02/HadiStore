package com.example.hadistore.service.impl;

import com.example.hadistore.dtos.request.CategoryRequest;
import com.example.hadistore.entity.Category;
import com.example.hadistore.exceptions.DataNotFoundException;
import com.example.hadistore.repository.CategoryRepository;
import com.example.hadistore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .status(true)
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Optional<Category> existCategory = categoryRepository.findById(id);
        if (existCategory.isEmpty()){
            throw new DataNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
