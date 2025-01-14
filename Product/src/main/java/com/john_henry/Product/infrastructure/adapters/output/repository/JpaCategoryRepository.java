package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.infrastructure.adapters.output.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
