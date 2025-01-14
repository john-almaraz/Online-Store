package com.john_henry.Product.infrastructure.adapters.output.mapper;

import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.infrastructure.adapters.output.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {

    Category toDomain(CategoryEntity categoryEntity);
    CategoryEntity toEntity(Category category);
    List<Category> toListDomain(List<CategoryEntity> categoryEntities);
    List<CategoryEntity> toListEntity(List<Category> categories);

}
