package com.john_henry.Product.application.service;

import com.john_henry.Product.application.dto.ProductDTO;
import com.john_henry.Product.application.mapper.ProductMapper;
import com.john_henry.Product.application.ports.input.ProductService;
import com.john_henry.Product.application.ports.output.CategoryRepository;
import com.john_henry.Product.application.ports.output.ProductRepository;
import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.domain.exception.CategoryNotFoundException;
import com.john_henry.Product.domain.exception.ProductNotFoundException;
import com.john_henry.Product.domain.exception.SellerNotFoundException;
import com.john_henry.Product.infrastructure.adapters.input.kafka.ListenerProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final KafkaTemplate<String,Integer> kafkaTemplate;
    private final ListenerProduct listenerProduct;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, KafkaTemplate<String, Integer> kafkaTemplate, ListenerProduct listenerProduct) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.listenerProduct = listenerProduct;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Integer categoryId = productDTO.getCategoryId();
        Integer sellerId = productDTO.getSellerId();
        Integer key = 4;

        if(categoryRepository.getCategoryById(categoryId).isEmpty())
            throw new CategoryNotFoundException("Category with id: " + categoryId +
                    " not found. You must use an existing category or create a new one");


        CompletableFuture<Integer> responseFuture = listenerProduct.registerFuture(key);
        kafkaTemplate.send("product-request-topic", sellerId);

        try {
            Integer response = responseFuture.get(15, TimeUnit.SECONDS);

            if (response == 1) {
                return productMapper.toDTO(productRepository.createProduct(productMapper.toEntity(productDTO)));
            } else {
                throw new SellerNotFoundException("Seller with id: " + sellerId +
                        " not found. You must use an existing sellerID or create a new one to register this product");
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout waiting for seller verification response", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error during seller verification execution", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted while waiting for seller verification", e);
        }

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
        if (productRepository.getProductById(id).isEmpty())
            throw new ProductNotFoundException("Product with id: " + id + " not found");

        productRepository.deleteProduct(id);

    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
        List<Product> products = productRepository.getProductsByCategoryId(categoryId);
        if (products.isEmpty())
            throw new ProductNotFoundException("Products with categoryID: " + categoryId + " not found");

        return productMapper.toListDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsBySellerId(Integer sellerId) {
        List<Product> products = productRepository.getProductsBySellerId(sellerId);
        if (products.isEmpty())
            throw new ProductNotFoundException("Products with sellerID: " + sellerId + " not found");

        return productMapper.toListDTO(products);
    }

}
