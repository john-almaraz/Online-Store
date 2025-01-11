package com.john_henry.User.domain.exception;

public class SellerNotFoundException extends RuntimeException{

    public SellerNotFoundException(String msg){
        super(msg);
    }
}
