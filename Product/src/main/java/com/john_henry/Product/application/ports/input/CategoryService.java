package com.john_henry.Product.application.ports.input;

import com.john_henry.Product.application.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(Integer id);
    List<CategoryDTO> getAllCategories();
    void updateCategory(Integer id, CategoryDTO categoryDTO);

}
