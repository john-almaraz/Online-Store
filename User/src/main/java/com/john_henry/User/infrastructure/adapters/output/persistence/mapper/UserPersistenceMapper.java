package com.john_henry.User.infrastructure.adapters.output.persistence.mapper;

import com.john_henry.User.domain.model.User;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);
    List<User> toListDomain(List<UserEntity> userEntityList);
    List<UserEntity> toListEntity(List<User> userDomainList);
}
