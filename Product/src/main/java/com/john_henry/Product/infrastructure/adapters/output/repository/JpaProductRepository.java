package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.infrastructure.adapters.output.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<ProductEntity,Integer> {
    List<ProductEntity> findAllByCategoryId(Integer categoryId);
}
