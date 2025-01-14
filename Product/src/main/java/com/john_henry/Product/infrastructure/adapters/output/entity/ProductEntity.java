package com.john_henry.Product.infrastructure.adapters.output.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String nameProduct;
    private String productDescription;
    @NotNull
    private Double price;
    @NotNull
    private Integer stock;
    private String image;
    @NotNull
    private Integer sellerId;
    @NotNull
    private Integer categoryId;
    private Double rating;
    private LocalDateTime creationDate;

}
