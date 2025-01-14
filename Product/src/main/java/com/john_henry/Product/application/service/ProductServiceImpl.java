package com.john_henry.Product.application.service;

import com.john_henry.Product.application.dto.ProductDTO;
import com.john_henry.Product.application.mapper.ProductMapper;
import com.john_henry.Product.application.ports.input.ProductService;
import com.john_henry.Product.application.ports.output.ProductRepository;
import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.domain.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productMapper.toDTO(productRepository.createProduct(productMapper.toEntity(productDTO)));
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.getProductById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product with id: " +id + " not found")
        );

        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.toListDTO(productRepository.getAllProducts());
    }

    @Override
    public void updateProduct(Integer id, ProductDTO productDTO) {
        Product product = productRepository.getProductById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id: " + id + " not found")
        );

        productMapper.updateFromDTO(productDTO,product);

        productRepository.updateProduct(id,product);
    }

    @Override
    public void deleteProduct(Integer id) {
        if (productRepository.getProductById(id).isPresent())
            throw new ProductNotFoundException("Product with id: " + id + "not found");

        productRepository.deleteProduct(id);

    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
        return productMapper.toListDTO(productRepository.getProductsByCategoryId(categoryId));
    }

    @Override
    public List<ProductDTO> getProductsBySellerId(Integer sellerId) {
        return productMapper.toListDTO(productRepository.getProductsBySellerId(sellerId));
    }
}
