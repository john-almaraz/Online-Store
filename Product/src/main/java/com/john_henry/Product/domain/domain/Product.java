package com.john_henry.Product.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

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
