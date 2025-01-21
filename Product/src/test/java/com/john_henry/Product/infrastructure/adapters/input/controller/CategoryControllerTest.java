package com.john_henry.Product.infrastructure.adapters.input.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.john_henry.Product.application.dto.CategoryDTO;
import com.john_henry.Product.application.ports.input.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createCategory_ShouldResponseEntityCategory_WhenRequestIsOk() throws Exception {
        String categoryName = "Gaming";

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(categoryName);

        mockMvc.perform(post("/category")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated());

        categoryController.createCategory(categoryDTO);
    }

    @Test
    void getCategoryByID_ShouldReturnCategory_WhenRequestIsOK() throws Exception {
        String categoryName = "Gaming";
        int categoryId = 1;

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(categoryName);
        categoryDTO.setId(categoryId);

        mockMvc.perform(get("/category/"+categoryId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk());

        categoryController.getCategoryById(categoryId);
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategories_WhenRequestIsOK() throws Exception {
        String categoryName = "Gaming";
        int categoryId = 1;

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(categoryName);
        categoryDTO.setId(categoryId);

        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(categoryDTO);

        mockMvc.perform(get("/category/allCategories")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categories)))
                .andExpect(status().isOk());

        categoryController.getAllCategories();
    }
    @Test
    void updateCategory_ShouldUpdateCategory_WhenRequestIsOK() throws Exception {
        String categoryName = "Gaming";
        int categoryId = 1;

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCategory(categoryName);
        categoryDTO.setId(categoryId);

        mockMvc.perform(put("/category/"+categoryId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isNoContent());

        categoryController.updateCategory(categoryId,categoryDTO);

    }

}