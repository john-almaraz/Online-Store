package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.infrastructure.adapters.output.persistence.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSellerRepository extends JpaRepository<SellerEntity,Integer> {
    Optional<SellerEntity> findByUserId(Integer userId);
}
