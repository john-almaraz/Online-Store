package com.john_henry.Product.application.mapper;

import com.john_henry.Product.application.dto.CategoryDTO;
import com.john_henry.Product.domain.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    Category toEntity(CategoryDTO categoryDTO);
    CategoryDTO toDTO(Category category);
    List<Category> toListEntity(List<CategoryDTO> categoryDTOList);
    List<CategoryDTO> toListDTO(List<Category> categoryList);

    @Mapping(target = "id",ignore = true)
    void updateFromDto(CategoryDTO categoryDTO, @MappingTarget Category category);

}
