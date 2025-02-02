package com.john_henry.User.application.ports.input;

import com.john_henry.User.application.dto.SellerDTO;

import java.util.List;

public interface SellerService {

    SellerDTO createSeller(SellerDTO sellerDTO);
    SellerDTO getSellerById(Integer sellerId);
    SellerDTO getSellerByUserId(Integer userId);
    List<SellerDTO> getAllSellers();
    void updateSeller(Integer sellerId, SellerDTO sellerDTO);
    void deleteSeller(Integer sellerId);
}
