package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.application.ports.output.ProductRepository;
import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.infrastructure.adapters.output.entity.ProductEntity;
import com.john_henry.Product.infrastructure.adapters.output.mapper.ProductPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;
    private final ProductPersistenceMapper productPersistenceMapper;

    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository, ProductPersistenceMapper productPersistenceMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.productPersistenceMapper = productPersistenceMapper;
    }

    @Override
    public Product createProduct(Product product) {
        return productPersistenceMapper.toDomain(jpaProductRepository.save(productPersistenceMapper.toEntity(product)));
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        Optional<ProductEntity> productEntity = jpaProductRepository.findById(id);

        return productEntity.map(productPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> getAllProducts() {
        return productPersistenceMapper.toListDomain(jpaProductRepository.findAll());
    }

    @Override
    public void updateProduct(Integer id, Product product) {
        Optional<ProductEntity> productEntity = jpaProductRepository.findById(id);

        if (productEntity.isPresent())
            jpaProductRepository.save(productPersistenceMapper.toEntity(product));
    }

    @Override
    public void deleteProduct(Integer id) {
        Optional<ProductEntity> productEntity = jpaProductRepository.findById(id);

        if (productEntity.isPresent())
            jpaProductRepository.deleteById(id);

    }

    @Override
    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productPersistenceMapper.toListDomain(jpaProductRepository.findAllByCategoryId(categoryId));
    }

    @Override
    public List<Product> getProductsBySellerId(Integer sellerId) {
        return List.of();
    }
}
