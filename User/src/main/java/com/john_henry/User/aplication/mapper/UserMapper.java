package com.john_henry.User.aplication.mapper;

import com.john_henry.User.aplication.dto.UserDTO;
import com.john_henry.User.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toEntity (UserDTO userDTO);
    UserDTO toDTO (User user);
    List<UserDTO> toListDTO (List<User> userList);
    List<User> toListEntity (List<UserDTO> userDTOList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void updateFromDto(UserDTO userDTO, @MappingTarget User user);

}
