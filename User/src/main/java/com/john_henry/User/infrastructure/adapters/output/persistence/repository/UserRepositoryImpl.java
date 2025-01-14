package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.application.ports.output.UserRepository;
import com.john_henry.User.domain.model.User;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.john_henry.User.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserPersistenceMapper userPersistenceMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = userPersistenceMapper.toEntity(user);
        return userPersistenceMapper.toDomain(jpaUserRepository.save(userEntity));
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(id);
        return userEntity.map(userPersistenceMapper::toDomain);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistenceMapper.toListDomain(jpaUserRepository.findAll());
    }

    @Override
    public void updateUser(Integer userId, User user) {
        Optional<UserEntity> userEntity = jpaUserRepository.findById(userId);
        if (userEntity.isPresent())
            jpaUserRepository.save(userPersistenceMapper.toEntity(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        jpaUserRepository.deleteById(userId);
    }
}
