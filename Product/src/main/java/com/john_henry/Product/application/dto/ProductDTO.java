package com.john_henry.Product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer id;
    private String nameProduct;
    private String productDescription;
    private Double price;
    private Integer stock;
    private String image;
    private Integer sellerId;
    private Integer categoryId;
    private Double rating;
    private LocalDateTime creationDate;

}
