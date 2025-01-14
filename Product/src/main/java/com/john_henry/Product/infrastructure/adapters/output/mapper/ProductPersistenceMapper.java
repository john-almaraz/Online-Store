package com.john_henry.Product.infrastructure.adapters.output.mapper;

import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.infrastructure.adapters.output.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    Product toDomain(ProductEntity productEntity);
    ProductEntity toEntity(Product product);
    List<Product> toListDomain(List<ProductEntity> entityList);
    List<ProductEntity> toListEntity(List<Product> productList);

}
