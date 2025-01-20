package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.infrastructure.adapters.output.entity.CategoryEntity;
import com.john_henry.Product.infrastructure.adapters.output.mapper.CategoryPersistenceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private CategoryPersistenceMapper categoryPersistenceMapper;

    @Mock
    private JpaCategoryRepository jpaCategoryRepository;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    @Test
    public void createCategory_ShouldReturnCategory_WhenCategoryIsCreated(){
        String nameCategory = "Gaming";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNameCategory(nameCategory);

        when(categoryPersistenceMapper.toEntity(category)).thenReturn(categoryEntity);
        when(jpaCategoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryPersistenceMapper.toDomain(categoryEntity)).thenReturn(category);

        Category result = categoryRepository.createCategory(category);

        assertEquals(category,result);
        verify(categoryPersistenceMapper).toEntity(category);
        verify(jpaCategoryRepository).save(categoryEntity);
        verify(categoryPersistenceMapper).toDomain(categoryEntity);

    }

    @Test
    public void getCategoryById_ShouldReturnCategory_WhenCategoryIsFound(){
        Integer categoryId = 1;

        Category category = new Category();
        category.setId(categoryId);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);

        when(jpaCategoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryPersistenceMapper.toDomain(categoryEntity)).thenReturn(category);

        Optional<Category> result = categoryRepository.getCategoryById(categoryId);

        assertTrue(result.isPresent());
        assertEquals(category,result.get());
        verify(jpaCategoryRepository).findById(categoryId);
        verify(categoryPersistenceMapper).toDomain(categoryEntity);

    }

    @Test
    public void getAllCategories_ShouldReturnCategoriesList_WhenCategoriesExist(){
        String nameCategory = "Gaming";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNameCategory(nameCategory);

        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities.add(categoryEntity);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(jpaCategoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryPersistenceMapper.toListDomain(categoryEntities)).thenReturn(categories);

        List<Category> resultList = categoryRepository.getAllCategories();

        assertEquals(categories,resultList);
        verify(jpaCategoryRepository).findAll();
        verify(categoryPersistenceMapper).toListDomain(categoryEntities);

    }

    @Test
    public void updateCategory_ShouldUpdateCategory_WhenCategoryIsFound(){
        Integer categoryId = 1;

        Category category = new Category();
        category.setId(categoryId);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);

        when(jpaCategoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryPersistenceMapper.toEntity(category)).thenReturn(categoryEntity);
        when(jpaCategoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        categoryRepository.updateCategory(categoryId,category);

        verify(jpaCategoryRepository).findById(categoryId);
        verify(categoryPersistenceMapper).toEntity(category);
        verify(jpaCategoryRepository).save(categoryEntity);

    }

}