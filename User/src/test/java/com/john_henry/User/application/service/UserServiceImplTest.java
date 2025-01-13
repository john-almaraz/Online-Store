package com.john_henry.User.application.service;

import com.john_henry.User.application.dto.UserDTO;
import com.john_henry.User.application.mapper.UserMapper;
import com.john_henry.User.application.ports.output.SellerRepository;
import com.john_henry.User.application.ports.output.UserRepository;
import com.john_henry.User.domain.exception.UserNotFoundException;
import com.john_henry.User.domain.model.Role;
import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ShouldReturnUserDTO_WhenUserIsCreated() {
        String username = "john123";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);

        User userEntity = new User();
        userEntity.setUsername(username);

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setUsername(username);

        when(userMapper.toEntity(userDTO)).thenReturn(userEntity);
        when(userRepository.createUser(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(expectedUserDTO);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(expectedUserDTO,result);
        verify(userMapper).toEntity(userDTO);
        verify(userRepository).createUser(userEntity);
        verify(userMapper).toDTO(userEntity);

    }

    @Test
    void getUserById_ShouldReturnUserDTO_WhenUserIsFound() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setId(userId);

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(userId);

        assertEquals(expectedUserDTO,result);
        verify(userRepository).getUserById(userId);
        verify(userMapper).toDTO(user);

    }

    @Test
    void getUserById_ShouldThrowException_WhenUserIsNotFound() {
        Integer userId = 1;
        String messageExpected = "User with id: 1 not found";

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(userId)
        );

        assertEquals(messageExpected, exception.getMessage());

        verify(userRepository).getUserById(userId);

    }

    @Test
    void getAllUsers_ShouldReturnUsers_WhenUsersExist(){
        Integer userId = 1;
        String username = "John99";
        String pwd = "john123";
        String email = "john@test.es";
        String numberPhone = "6666666";
        Role rol = Role.CLIENT;
        LocalDateTime registrationDate = LocalDateTime.now();
        Boolean userActive = true;

        List<User> users = new ArrayList<>();
        users.add(new User(userId,username,pwd,email,numberPhone,rol,registrationDate,userActive));

        List<UserDTO> usersExpected = new ArrayList<>();
        usersExpected.add(new UserDTO(userId,username,pwd,email,numberPhone,rol,registrationDate,userActive));

        when(userRepository.getAllUsers()).thenReturn(users);
        when(userMapper.toListDTO(users)).thenReturn(usersExpected);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(usersExpected,result);
        verify(userRepository).getAllUsers();
        verify(userMapper).toListDTO(users);

    }

    @Test
    void updateUser_ShouldThrowException_WhenUserIsNotFound() {
        Integer userId = 1;
        String messageExpected = "User with id: 1 not found";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(userId,userDTO)
        );

        assertEquals(messageExpected, exception.getMessage());

        verify(userRepository).getUserById(userId);

    }

    @Test
    void updateUser_ShouldUpdateUserWithoutErrors_WhenUserExist() {
        Integer userId = 1;
        String userName = "John";

        User user = new User();
        user.setId(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername(userName);

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateFromDto(userDTO,user);
        doNothing().when(userRepository).updateUser(userId,user);

        userService.updateUser(userId,userDTO);

        verify(userRepository).getUserById(userId);
        verify(userMapper).updateFromDto(userDTO,user);
        verify(userRepository).updateUser(userId,user);

    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserIsNotFound() {
        Integer userId = 1;
        String messageExpected = "User with id: 1 not found";

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(userId)
        );

        assertEquals(messageExpected, exception.getMessage());

        verify(userRepository).getUserById(userId);

    }

    @Test
    void deleteUser_ShouldDeleteUserWithoutErrors_WhenUserExist(){
        Integer userId = 1;
        Integer sellerId = 2;

        User user = new User();
        user.setId(userId);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setUserId(userId);

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(sellerRepository.getSellerByUserId(userId)).thenReturn(Optional.of(seller));
        doNothing().when(sellerRepository).deleteSeller(sellerId);
        doNothing().when(userRepository).deleteUser(userId);

        userService.deleteUser(userId);

        verify(sellerRepository).getSellerByUserId(userId);
        verify(sellerRepository).deleteSeller(sellerId);
        verify(userRepository).deleteUser(userId);

    }

}