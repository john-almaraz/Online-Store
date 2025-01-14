package com.john_henry.User.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {


    private Integer id;
    private Integer userId;
    private String nameStore;
    private String logoStore;
    private String descriptionStore;

}
