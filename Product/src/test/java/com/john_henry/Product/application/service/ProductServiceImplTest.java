package com.john_henry.Product.application.service;

import com.john_henry.Product.application.dto.ProductDTO;
import com.john_henry.Product.application.mapper.ProductMapper;
import com.john_henry.Product.application.ports.output.CategoryRepository;
import com.john_henry.Product.application.ports.output.ProductRepository;
import com.john_henry.Product.domain.domain.Category;
import com.john_henry.Product.domain.domain.Product;
import com.john_henry.Product.domain.exception.CategoryNotFoundException;
import com.john_henry.Product.domain.exception.ProductNotFoundException;
import com.john_henry.Product.domain.exception.SellerNotFoundException;
import com.john_henry.Product.infrastructure.adapters.input.kafka.ListenerProduct;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private KafkaTemplate<String,Integer> kafkaTemplate;

    @Mock
    private ListenerProduct listenerProduct;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void createProduct_ShouldReturnProduct_WhenProductIsCreated(){
        Integer categoryId = 1;
        Integer sellerId = 2 ;
        int keyFuture = 4;
        int responseCompleteFuture = 1;
        String topicName = "product-request-topic";
        CompletableFuture<Integer> responseFuture = new CompletableFuture<>();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);
        productDTO.setSellerId(sellerId);

        Product product = new Product();
        product.setCategoryId(categoryId);
        product.setSellerId(sellerId);

        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(listenerProduct.registerFuture(keyFuture)).thenReturn(responseFuture);
        when(kafkaTemplate.send(topicName,sellerId)).thenReturn(null);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.createProduct(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);
        responseFuture.complete(responseCompleteFuture);

        ProductDTO result = productService.createProduct(productDTO);

        assertEquals(productDTO,result);
        verify(categoryRepository).getCategoryById(categoryId);
        verify(listenerProduct).registerFuture(keyFuture);
        verify(kafkaTemplate).send(topicName,sellerId);
        verify(productMapper).toEntity(productDTO);
        verify(productRepository).createProduct(product);
        verify(productMapper).toDTO(product);

    }

    @Test
    public void createProduct_ShouldThrowCategoryNotFoundException_WhenCategoryIsNotFound(){
        Integer categoryId = 1;
        String messageExpected = "Category with id: " + categoryId +
                " not found. You must use an existing category or create a new one";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.empty());

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                ()-> productService.createProduct(productDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(categoryRepository).getCategoryById(categoryId);

    }

    @Test
    public void createProduct_ShouldThrowSellerNotFoundException_WhenSellerIsNotFound(){
        Integer categoryId = 1;
        Integer sellerId = 2 ;
        int keyFuture = 4;
        int responseCompleteFuture = 0;
        String topicName = "product-request-topic";
        CompletableFuture<Integer> responseFuture = new CompletableFuture<>();
        String messageExpected = "Seller with id: " + sellerId +
                " not found. You must use an existing sellerID or create a new one to register this product";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);
        productDTO.setSellerId(sellerId);

        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(listenerProduct.registerFuture(keyFuture)).thenReturn(responseFuture);
        when(kafkaTemplate.send(topicName,sellerId)).thenReturn(null);
        responseFuture.complete(responseCompleteFuture);

        SellerNotFoundException exception = assertThrows(SellerNotFoundException.class,
                ()-> productService.createProduct(productDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(categoryRepository).getCategoryById(categoryId);
        verify(listenerProduct).registerFuture(keyFuture);
        verify(kafkaTemplate).send(topicName,sellerId);

    }

    @Test
    public void createProduct_ShouldThrowTimeException_WhenWaitingTimeIsUp(){
        Integer categoryId = 1;
        Integer sellerId = 2 ;
        int keyFuture = 4;
        String topicName = "product-request-topic";
        CompletableFuture<Integer> responseFuture = new CompletableFuture<>();
        String messageExpected = "Timeout waiting for seller verification response";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);
        productDTO.setSellerId(sellerId);

        Category category = new Category();
        category.setId(categoryId);

        when(categoryRepository.getCategoryById(categoryId)).thenReturn(Optional.of(category));
        when(listenerProduct.registerFuture(keyFuture)).thenReturn(responseFuture);
        when(kafkaTemplate.send(topicName,sellerId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                ()-> productService.createProduct(productDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(categoryRepository).getCategoryById(categoryId);
        verify(listenerProduct).registerFuture(keyFuture);
        verify(kafkaTemplate).send(topicName,sellerId);

    }

    @Test
    public void getProductById_ShouldReturnProduct_WhenProductIsFound(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(productId);

        assertEquals(productDTO,result);
        verify(productRepository).getProductById(productId);
        verify(productMapper).toDTO(product);

    }

    @Test
    public void getProductId_ShouldThrowProductNotFoundException_WhenProductIsNotFound(){
        int productId = 1;
        String messageExpected = "Product with id: " + productId + " not found";

        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(productRepository).getProductById(productId);

    }

    @Test
    public void getAllProducts_ShouldProductList_WhenProductsExist(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        List<Product> products = new ArrayList<>();
        products.add(product);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS.add(productDTO);

        when(productRepository.getAllProducts()).thenReturn(products);
        when(productMapper.toListDTO(products)).thenReturn(productDTOS);

        List<ProductDTO> resultList = productService.getAllProducts();

        assertEquals(productDTOS,resultList);
        verify(productRepository).getAllProducts();
        verify(productMapper).toListDTO(products);

    }

    @Test
    public void updateProduct_ShouldUpdateProduct_WhenProductIdExist(){
        Integer productId = 1;
        String name = "TV 45";

        Product product = new Product();
        product.setId(productId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product));

        productDTO.setNameProduct(name);
        doNothing().when(productMapper).updateFromDTO(productDTO,product);
        doNothing().when(productRepository).updateProduct(productId,product);

        productService.updateProduct(productId,productDTO);

        verify(productRepository).getProductById(productId);
        verify(productMapper).updateFromDTO(productDTO,product);
        verify(productRepository).updateProduct(productId,product);

    }

    @Test
    public void updateProduct_ShouldThrowProductNotFoundException_WhenProductIsNotFound(){
        int productId = 1;
        String messageExpected = "Product with id: " + productId + " not found";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(productId,productDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(productRepository).getProductById(productId);

    }

    @Test
    public void deleteProduct_ShouldDeleteProduct_WhenProductIsFound(){
        Integer productId = 1;

        Product product = new Product();
        product.setId(productId);

        when(productRepository.getProductById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteProduct(productId);

        productService.deleteProduct(productId);

        verify(productRepository).getProductById(productId);
        verify(productRepository).deleteProduct(productId);

    }

    @Test
    public void deleteProduct_ShouldThrowProductNotFoundException_WhenProductIsNotFound(){
        int productId = 1;
        String messageExpected = "Product with id: " + productId + " not found";

        when(productRepository.getProductById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(productId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(productRepository).getProductById(productId);

    }

    @Test
    public void getProductsByCategoryId_ShouldReturnProductList_WhenProductsExistInCategoryID(){
        Integer categoryId = 1;

        Product product = new Product();
        product.setCategoryId(categoryId);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(categoryId);

        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO);

        when(productRepository.getProductsByCategoryId(categoryId)).thenReturn(productList);
        when(productMapper.toListDTO(productList)).thenReturn(productDTOList);

        List<ProductDTO> resultList = productService.getProductsByCategoryId(categoryId);

        assertEquals(productDTOList,resultList);
        verify(productRepository).getProductsByCategoryId(categoryId);
        verify(productMapper).toListDTO(productList);

    }

    @Test
    public void getProductsByCategoryId_ShouldThrowProductNotFoundException_WhenProductsListIsEmpty(){
        Integer categoryId = 1;
        String messageExpected = "Products with categoryID: " + categoryId + " not found";

        List<Product> products = new ArrayList<>();

        when(productRepository.getProductsByCategoryId(categoryId)).thenReturn(products);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductsByCategoryId(categoryId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(productRepository).getProductsByCategoryId(categoryId);

    }

    @Test
    public void getProductsBySellerId_ShouldReturnProductList_WhenProductsExistInSellerID(){
        Integer sellerId = 1;

        Product product = new Product();
        product.setSellerId(sellerId);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setSellerId(sellerId);

        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO);

        when(productRepository.getProductsBySellerId(sellerId)).thenReturn(productList);
        when(productMapper.toListDTO(productList)).thenReturn(productDTOList);

        List<ProductDTO> resultList = productService.getProductsBySellerId(sellerId);

        assertEquals(productDTOList,resultList);
        verify(productRepository).getProductsBySellerId(sellerId);
        verify(productMapper).toListDTO(productList);

    }

    @Test
    public void getProductsBySellerId_ShouldThrowProductNotFoundException_WhenProductsListIsEmpty(){
        Integer sellerId = 1;
        String messageExpected = "Products with sellerID: " + sellerId + " not found";

        List<Product> products = new ArrayList<>();

        when(productRepository.getProductsBySellerId(sellerId)).thenReturn(products);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductsBySellerId(sellerId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(productRepository).getProductsBySellerId(sellerId);

    }

}