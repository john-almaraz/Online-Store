package com.john_henry.User.infrastructure.adapters.output.persistence.mapper;

import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.SellerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerPersistenceMapper {
    SellerEntity toEntity(Seller seller);
    Seller toDomain(SellerEntity sellerEntity);
    List<Seller> toListDomain(List<SellerEntity> sellerEntityList);
    List<SellerEntity> toListEntity(List<Seller> sellerList);
}
