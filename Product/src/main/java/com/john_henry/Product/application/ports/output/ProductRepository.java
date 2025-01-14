package com.john_henry.Product.application.ports.output;

import com.john_henry.Product.domain.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product createProduct(Product product);
    Optional<Product> getProductById(Integer id);
    List<Product> getAllProducts();
    void updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
    List<Product> getProductsByCategoryId(Integer categoryId);
    List<Product> getProductsBySellerId(Integer sellerId);

}
