package com.john_henry.User.application.ports.output;

import com.john_henry.User.domain.model.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerRepository {
    Seller createSeller(Seller seller);
    Optional<Seller> getSellerById(Integer sellerId);
    Optional<Seller> getSellerByUserId(Integer userId);
    List<Seller> getAllSellers();
    void updateSeller(Integer sellerId, Seller seller);
    void deleteSeller(Integer sellerId);
}
