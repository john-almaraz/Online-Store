package com.john_henry.Product.application.ports.output;

import com.john_henry.Product.domain.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category createCategory(Category category);
    Optional<Category> getCategoryById(Integer id);
    List<Category> getAllCategories();
    void updateCategory(Integer id, Category category);
}
