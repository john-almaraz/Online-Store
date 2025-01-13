package com.john_henry.User.application.service;

import com.john_henry.User.application.dto.UserDTO;
import com.john_henry.User.application.mapper.UserMapper;
import com.john_henry.User.application.ports.input.UserService;
import com.john_henry.User.application.ports.output.SellerRepository;
import com.john_henry.User.application.ports.output.UserRepository;
import com.john_henry.User.domain.exception.UserNotFoundException;
import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.domain.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SellerRepository sellerRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.sellerRepository = sellerRepository;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserActive(true);
        userDTO.setRegistrationDate(LocalDateTime.now());
        return userMapper.toDTO(userRepository.createUser(userMapper.toEntity(userDTO)));
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepository.getUserById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id: " + userId + " not found")
        );

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.toListDTO(userRepository.getAllUsers());
    }

    @Override
    public void updateUser(Integer userId, UserDTO userDTO) {
        User user = userRepository.getUserById(userId).orElseThrow(
                () -> new UserNotFoundException("User with id: " + userId + " not found")
        );

        userMapper.updateFromDto(userDTO,user);

        userRepository.updateUser(userId,user);
    }

    @Override
    public void deleteUser(Integer userId) {
        if (userRepository.getUserById(userId).isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        Optional<Seller> seller = sellerRepository.getSellerByUserId(userId);
        seller.ifPresent(value -> sellerRepository.deleteSeller(value.getId()));

        userRepository.deleteUser(userId);
    }

}
