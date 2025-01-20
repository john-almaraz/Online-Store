package com.john_henry.Product.application.service;

import com.john_henry.Product.application.dto.CategoryDTO;
import com.john_henry.Product.application.mapper.CategoryMapper;
import com.john_henry.Product.application.ports.output.CategoryRepository;
import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.domain.exception.CategoryNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void createCategory_ShouldReturnCategory_WhenCategoryIsCreated(){
        String nameCategory = "Gaming";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(nameCategory);

        when(categoryMapper.toEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.createCategory(category)).thenReturn(category);
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.createCategory(categoryDTO);

        assertEquals(categoryDTO,result);
        verify(categoryMapper).toEntity(categoryDTO);
        verify(categoryRepository).createCategory(category);
        verify(categoryMapper).toDTO(category);

    }

    @Test
    public void getCategoryId_ShouldReturnCategory_WhenCategoryIsFound(){
        Integer categoryId = 1;
        String nameCategory = "Gaming";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(nameCategory);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryById(categoryId);

        assertEquals(categoryDTO,result);
        verify(categoryRepository).getCategoryById(categoryId);
        verify(categoryMapper).toDTO(category);

    }

    @Test
    public void getCategoryId_ShouldThrowCategoryNotFoundException_WhenCategoryIsNotFound(){
        int categoryId = 1;
        String messageExpected = "Category with id: " + categoryId + " not found";

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(categoryId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(categoryRepository).getCategoryById(categoryId);

    }

    @Test
    public void getAllCategory_ShouldReturnCategoryList_WhenExistCategories(){
        Integer categoryId = 1;
        String nameCategory = "Gaming";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(nameCategory);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        categoriesDTO.add(categoryDTO);

        when(categoryRepository.getAllCategories()).thenReturn(categories);
        when(categoryMapper.toListDTO(categories)).thenReturn(categoriesDTO);

        List<CategoryDTO> listResult = categoryService.getAllCategories();

        assertEquals(categoriesDTO,listResult);
        verify(categoryRepository).getAllCategories();
        verify(categoryMapper).toListDTO(categories);

    }

    @Test
    public void updateCategory_ShouldUpdateCategory_WhenCategoryIsFound(){
        Integer categoryId = 1;
        String nameCategory = "Gaming";
        String nameCategoryAct = "Gaming updated";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(nameCategory);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        categoryDTO.setNameCategory(nameCategoryAct);
        doNothing().when(categoryMapper).updateFromDto(categoryDTO,category);
        doNothing().when(categoryRepository).updateCategory(categoryId,category);

        categoryService.updateCategory(categoryId,categoryDTO);

        verify(categoryRepository).getCategoryById(categoryId);
        verify(categoryMapper).updateFromDto(categoryDTO,category);
        verify(categoryRepository).updateCategory(categoryId,category);

    }

    @Test
    public void updateCategory_ShouldThrowCategoryNotFoundException_WhenCategoryIsNotFound(){
        int categoryId = 1;
        String nameCategory = "Gaming";
        String messageExpected = "Category with id: " + categoryId + " not found";

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(nameCategory);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.updateCategory(categoryId,categoryDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(categoryRepository).getCategoryById(categoryId);

    }

}