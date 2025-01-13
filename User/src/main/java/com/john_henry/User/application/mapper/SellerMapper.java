package com.john_henry.User.application.mapper;

import com.john_henry.User.application.dto.SellerDTO;
import com.john_henry.User.domain.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SellerMapper {

    Seller toEntity(SellerDTO sellerDTO);
    SellerDTO toDTO (Seller seller);
    List<Seller> toListEntity(List<SellerDTO> sellerDTOList);
    List<SellerDTO> toListDTO(List<Seller> sellerList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    void updateFromDto(SellerDTO sellerDTO, @MappingTarget Seller seller);
}
