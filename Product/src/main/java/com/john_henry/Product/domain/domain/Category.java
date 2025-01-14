package com.john_henry.Product.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Integer id;
    private String nameCategory;
    private String categoryDescription;

}
