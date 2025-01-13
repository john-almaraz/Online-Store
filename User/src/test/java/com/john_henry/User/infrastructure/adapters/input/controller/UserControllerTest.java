package com.john_henry.User.infrastructure.adapters.input.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.john_henry.User.application.dto.UserDTO;
import com.john_henry.User.application.ports.input.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createUser_ShouldReturnUser_WhenRequestIsOK() throws Exception {
        String username = "John123";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        userController.createUser(userDTO);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers_WhenRequestIsOK() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);

        List<UserDTO> users = new ArrayList<>();
        users.add(userDTO);

        mockMvc.perform(get("/users/allUsers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk());

        userController.getAllUsers();
    }

    @Test
    void getUserByID_ShouldReturnUser_WhenRequestIsOK() throws Exception {
        Integer userID = 1;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userID);

        mockMvc.perform(get("/users/"+userID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        userController.getUserById(userID);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenRequestIsOK() throws Exception {
        Integer userID = 1;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userID);

        mockMvc.perform(delete("/users/"+userID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNoContent());

        userController.deleteUser(userID);
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenRequestIsOK() throws Exception {
        Integer userID = 1;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userID);

        mockMvc.perform(put("/users/"+userID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNoContent());

        userController.updateUser(userID, userDTO);
    }


}