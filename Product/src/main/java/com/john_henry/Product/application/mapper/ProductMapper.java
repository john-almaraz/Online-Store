package com.john_henry.Product.application.mapper;

import com.john_henry.Product.application.dto.ProductDTO;
import com.john_henry.Product.domain.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductDTO productDTO);
    ProductDTO toDTO(Product product);
    List<Product> toListEntity(List<ProductDTO> productDTOList);
    List<ProductDTO> toListDTO(List<Product> productList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sellerId", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    void updateFromDTO(ProductDTO productDTO, @MappingTarget Product product);

}
