package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.aplication.ports.output.SellerRepository;
import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.SellerEntity;
import com.john_henry.User.infrastructure.adapters.output.persistence.mapper.SellerPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SellerRepositoryImpl implements SellerRepository {

    private final JpaSellerRepository jpaSellerRepository;
    private final SellerPersistenceMapper sellerPersistenceMapper;

    public SellerRepositoryImpl(JpaSellerRepository jpaSellerRepository, SellerPersistenceMapper sellerPersistenceMapper) {
        this.jpaSellerRepository = jpaSellerRepository;
        this.sellerPersistenceMapper = sellerPersistenceMapper;
    }

    @Override
    public Seller createSeller(Seller seller) {
        SellerEntity sellerEntity = sellerPersistenceMapper.toEntity(seller);

        return sellerPersistenceMapper.toDomain(jpaSellerRepository.save(sellerEntity));
    }

    @Override
    public Optional<Seller> getSellerById(Integer sellerId) {
        Optional<SellerEntity> sellerEntity = jpaSellerRepository.findById(sellerId);

        return sellerEntity.map(sellerPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Seller> getSellerByUserId(Integer userId) {
        Optional<SellerEntity> sellerEntity = jpaSellerRepository.findByUserId(userId);

        return sellerEntity.map(sellerPersistenceMapper::toDomain);
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerPersistenceMapper.toListDomain(jpaSellerRepository.findAll());
    }

    @Override
    public void updateSeller(Integer sellerId, Seller seller) {
        Optional<SellerEntity> sellerEntity = jpaSellerRepository.findById(sellerId);
        if(sellerEntity.isPresent())
            jpaSellerRepository.save(sellerPersistenceMapper.toEntity(seller));
    }

    @Override
    public void deleteSeller(Integer sellerId) {
        jpaSellerRepository.deleteById(sellerId);
    }
}
