package com.john_henry.Product.infrastructure.adapters.input.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.john_henry.Product.application.dto.ProductDTO;
import com.john_henry.Product.application.ports.input.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createProduct_ShouldReturnProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);

        mockMvc.perform(post("/product")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated());

        productController.createProduct(productDTO);
    }

    @Test
    void getAllProducts_ShouldReturnListOfProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);

        List<ProductDTO> products = new ArrayList<>();
        products.add(productDTO);

        mockMvc.perform(get("/product/allProducts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(products)))
                .andExpect(status().isOk());

        productController.getAllProducts();
    }

    @Test
    void getProductByID_ShouldReturnProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";
        Integer productId = 1;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);
        productDTO.setId(productId);

        mockMvc.perform(get("/product/"+productId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        productController.getProductById(productId);
    }

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";
        Integer productId = 1;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);
        productDTO.setId(productId);

        mockMvc.perform(delete("/product/"+productId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNoContent());

        productController.deleteProduct(productId);
    }

    @Test
    void updateProduct_ShouldUpdateProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";
        Integer productId = 1;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);
        productDTO.setId(productId);

        mockMvc.perform(put("/product/"+productId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNoContent());

        productController.updateProduct(productId, productDTO);
    }

    @Test
    void getProductByCategoryID_ShouldReturnProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";
        Integer categoryId = 1;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);
        productDTO.setCategoryId(categoryId);

        mockMvc.perform(get("/product/category/"+categoryId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        productController.getProductsByCategoryId(categoryId);
    }

    @Test
    void getProductBySellerID_ShouldReturnProduct_WhenRequestIsOK() throws Exception {
        String productName = "Tv 45";
        Integer sellerId = 1;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setNameProduct(productName);
        productDTO.setSellerId(sellerId);

        mockMvc.perform(get("/product/sellerId/"+sellerId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        productController.getProductsBySellerId(sellerId);
    }

}