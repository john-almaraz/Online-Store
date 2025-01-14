package com.john_henry.Product.application.service;

import com.john_henry.Product.application.dto.CategoryDTO;
import com.john_henry.Product.application.mapper.CategoryMapper;
import com.john_henry.Product.application.ports.input.CategoryService;
import com.john_henry.Product.application.ports.output.CategoryRepository;
import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.domain.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return categoryMapper.toDTO(categoryRepository.createCategory(categoryMapper.toEntity(categoryDTO)));
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        Category category = categoryRepository.getCategoryById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category with id: " + id + " not found")
        );

        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toListDTO(categoryRepository.getAllCategories());
    }

    @Override
    public void updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.getCategoryById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category with id: " + id + " not found")
        );

        categoryMapper.updateFromDto(categoryDTO,category);

        categoryRepository.updateCategory(id,category);
    }
}
