package com.john_henry.Product.infrastructure.adapters.input.controller;

import com.john_henry.Product.application.dto.CategoryDTO;
import com.john_henry.Product.application.ports.input.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<CategoryDTO>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/allCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryId,categoryDTO);

        return ResponseEntity.noContent().build();
    }

}
