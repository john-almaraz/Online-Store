package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.domain.model.User;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.john_henry.User.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    UserPersistenceMapper userPersistenceMapper;

    @InjectMocks UserRepositoryImpl userRepository;

    @Test
    public void createUser_ShouldReturnUser_WhenUserIsCreated(){
        String username = "John123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User userExpected = new User();
        userExpected.setUsername(username);

        when(userPersistenceMapper.toEntity(userExpected)).thenReturn(userEntity);
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
        when(userPersistenceMapper.toDomain(userEntity)).thenReturn(userExpected);

        User result = userRepository.createUser(userExpected);

        assertEquals(userExpected,result);
        verify(userPersistenceMapper).toEntity(userExpected);
        verify(jpaUserRepository).save(userEntity);
        verify(userPersistenceMapper).toDomain(userEntity);

    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExist(){
        Integer id = 1;
        String username = "John123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User userExpected = new User();
        userExpected.setUsername(username);

        when(jpaUserRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userPersistenceMapper.toDomain(userEntity)).thenReturn(userExpected);

        Optional<User> result = userRepository.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(userExpected,result.get());
        verify(jpaUserRepository).findById(id);
        verify(userPersistenceMapper).toDomain(userEntity);

    }

    @Test
    public void getAllUsers_ShouldReturnListOfUsers_WhenUsersExist(){
        Integer id = 1;
        String username = "John123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User user = new User();
        user.setUsername(username);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);

        List<User> users = new ArrayList<>();
        users.add(user);

        when(jpaUserRepository.findAll()).thenReturn(userEntities);
        when(userPersistenceMapper.toListDomain(userEntities)).thenReturn(users);

        List<User> listResult = userRepository.getAllUsers();

        assertEquals(users,listResult);
        verify(jpaUserRepository).findAll();
        verify(userPersistenceMapper).toListDomain(userEntities);

    }

    @Test
    public void updateUser_ShouldUpdateUser_WhenUserExist(){
        Integer id = 1;
        String username = "John123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User user = new User();
        user.setUsername(username);

        when(jpaUserRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userPersistenceMapper.toEntity(user)).thenReturn(userEntity);
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);

        userRepository.updateUser(id,user);

        verify(jpaUserRepository).findById(id);
        verify(userPersistenceMapper).toEntity(user);
        verify(jpaUserRepository).save(userEntity);

    }

    @Test
    public void deleteUser_ShouldDeleteUser_WhenUserExist(){
        Integer id = 1;

        doNothing().when(jpaUserRepository).deleteById(id);

        userRepository.deleteUser(id);

        verify(jpaUserRepository).deleteById(id);

    }

}