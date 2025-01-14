package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.application.ports.output.CategoryRepository;
import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.infrastructure.adapters.output.entity.CategoryEntity;
import com.john_henry.Product.infrastructure.adapters.output.mapper.CategoryPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryPersistenceMapper categoryPersistenceMapper;
    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryRepositoryImpl(CategoryPersistenceMapper categoryPersistenceMapper, JpaCategoryRepository jpaCategoryRepository) {
        this.categoryPersistenceMapper = categoryPersistenceMapper;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryPersistenceMapper.toDomain(jpaCategoryRepository.save(categoryPersistenceMapper.toEntity(category)));
    }

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        Optional<CategoryEntity> categoryEntity = jpaCategoryRepository.findById(id);

        return categoryEntity.map(categoryPersistenceMapper::toDomain);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryPersistenceMapper.toListDomain(jpaCategoryRepository.findAll());
    }

    @Override
    public void updateCategory(Integer id, Category category) {
        Optional<CategoryEntity> categoryEntity = jpaCategoryRepository.findById(id);

        if (categoryEntity.isPresent())
            jpaCategoryRepository.save(categoryPersistenceMapper.toEntity(category));
    }
}
