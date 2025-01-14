package com.john_henry.Product.domain.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String msg){
        super(msg);
    }

}
