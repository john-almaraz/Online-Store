package com.john_henry.User.aplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private Integer id;
    private Integer userId;
    private String nameStore;
    private String logoStore;
    private String descriptionStore;
}
