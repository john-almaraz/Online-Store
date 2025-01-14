package com.john_henry.User.application.ports.input;

import com.john_henry.User.application.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void updateUser(Integer userId, UserDTO userDTO);
    void deleteUser(Integer userId);
}
