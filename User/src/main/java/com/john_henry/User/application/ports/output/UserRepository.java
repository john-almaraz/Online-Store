package com.john_henry.User.application.ports.output;

import com.john_henry.User.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);
    Optional<User> getUserById(Integer userId);
    List<User> getAllUsers();
    void updateUser(Integer userId, User user);
    void deleteUser(Integer userId);

}
