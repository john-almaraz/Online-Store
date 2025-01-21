package com.john_henry.Product.infrastructure.adapters.output.repository;

import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.infrastructure.adapters.output.entity.ProductEntity;
import com.john_henry.Product.infrastructure.adapters.output.mapper.ProductPersistenceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    @Mock
    private ProductPersistenceMapper productPersistenceMapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    public void createProduct_ShouldReturnProduct_WhenProductIsCreated(){
        String productName = "TV 45";

        Product product = new Product();
        product.setNameProduct(productName);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setNameProduct(productName);

        when(productPersistenceMapper.toEntity(product)).thenReturn(productEntity);
        when(jpaProductRepository.save(productEntity)).thenReturn(productEntity);
        when(productPersistenceMapper.toDomain(productEntity)).thenReturn(product);

        Product result = productRepository.createProduct(product);

        assertEquals(product,result);
        verify(productPersistenceMapper).toEntity(product);
        verify(jpaProductRepository).save(productEntity);
        verify(productPersistenceMapper).toDomain(productEntity);

    }

    @Test
    public void getProductById_ShouldReturnProduct_WhenProductExist(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        when(jpaProductRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productPersistenceMapper.toDomain(productEntity)).thenReturn(product);

        Optional<Product> result = productRepository.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product,result.get());
        verify(jpaProductRepository).findById(productId);
        verify(productPersistenceMapper).toDomain(productEntity);

    }

    @Test
    public void getAllProduct_ShouldReturnProductList_WhenProductsExist(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        List<Product> productsList = new ArrayList<>();
        productsList.add(product);

        List<ProductEntity> productsEntities = new ArrayList<>();
        productsEntities.add(productEntity);

        when(jpaProductRepository.findAll()).thenReturn(productsEntities);
        when(productPersistenceMapper.toListDomain(productsEntities)).thenReturn(productsList);

        List<Product> result = productRepository.getAllProducts();

        assertEquals(productsList,result);
        verify(jpaProductRepository).findAll();
        verify(productPersistenceMapper).toListDomain(productsEntities);

    }

    @Test
    public void updateProduct_ShouldUpdateProduct_WhenProductExist(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        when(jpaProductRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productPersistenceMapper.toEntity(product)).thenReturn(productEntity);
        when(jpaProductRepository.save(productEntity)).thenReturn(productEntity);

        productRepository.updateProduct(productId,product);

        verify(jpaProductRepository).findById(productId);
        verify(productPersistenceMapper).toEntity(product);
        verify(jpaProductRepository).save(productEntity);

    }

    @Test
    public void deleteProduct_ShouldDeleteProduct_WhenProductExist(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);

        when(jpaProductRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        doNothing().when(jpaProductRepository).deleteById(productId);

        productRepository.deleteProduct(productId);

        verify(jpaProductRepository).findById(productId);
        verify(jpaProductRepository).deleteById(productId);

    }

    @Test
    public void getAllProductByCategoryId_ShouldReturnProductList_WhenProductsExistInCategory(){
        Integer categoryId = 1;

        Product product = new Product();
        product.setCategoryId(categoryId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategoryId(categoryId);

        List<Product> productsList = new ArrayList<>();
        productsList.add(product);

        List<ProductEntity> productsEntities = new ArrayList<>();
        productsEntities.add(productEntity);

        when(jpaProductRepository.findAllByCategoryId(categoryId)).thenReturn(productsEntities);
        when(productPersistenceMapper.toListDomain(productsEntities)).thenReturn(productsList);

        List<Product> result = productRepository.getProductsByCategoryId(categoryId);

        assertEquals(productsList,result);
        verify(jpaProductRepository).findAllByCategoryId(categoryId);
        verify(productPersistenceMapper).toListDomain(productsEntities);

    }

    @Test
    public void getAllProductBySellerId_ShouldReturnProductList_WhenSellersHaveProducts(){
        Integer sellerId = 1;

        Product product = new Product();
        product.setSellerId(sellerId);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setSellerId(sellerId);

        List<Product> productsList = new ArrayList<>();
        productsList.add(product);

        List<ProductEntity> productsEntities = new ArrayList<>();
        productsEntities.add(productEntity);

        when(jpaProductRepository.findAllBySellerId(sellerId)).thenReturn(productsEntities);
        when(productPersistenceMapper.toListDomain(productsEntities)).thenReturn(productsList);

        List<Product> result = productRepository.getProductsBySellerId(sellerId);

        assertEquals(productsList,result);
        verify(jpaProductRepository).findAllBySellerId(sellerId);
        verify(productPersistenceMapper).toListDomain(productsEntities);

    }

}