package com.john_henry.Product.application.ports.input;

import com.john_henry.Product.application.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(Integer id);
    List<ProductDTO> getAllProducts();
    void updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
    List<ProductDTO> getProductsByCategoryId(Integer categoryId);
    List<ProductDTO> getProductsBySellerId(Integer sellerId);
}
