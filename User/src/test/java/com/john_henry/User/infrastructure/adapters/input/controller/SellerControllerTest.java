package com.john_henry.User.infrastructure.adapters.input.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.john_henry.User.application.dto.SellerDTO;
import com.john_henry.User.application.ports.input.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class SellerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private SellerController sellerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createSeller_ShouldReturnSeller_WhenRequestIsOK() throws Exception {
        String nameStore = "John Store";

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setNameStore(nameStore);

        mockMvc.perform(post("/sellers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isCreated());

        sellerController.createSeller(sellerDTO);
    }

    @Test
    void getAllSellers_ShouldReturnListOfUsers_WhenRequestIsOK() throws Exception {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(1);

        List<SellerDTO> sellers = new ArrayList<>();
        sellers.add(sellerDTO);

        mockMvc.perform(get("/sellers/allSellers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellers)))
                .andExpect(status().isOk());

        sellerController.getAllSellers();
    }

    @Test
    void getSellerByID_ShouldReturnSeller_WhenRequestIsOK() throws Exception {
        Integer id = 1;

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(id);

        mockMvc.perform(get("/sellers/"+id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isOk());

        sellerController.getSellerById(id);
    }

    @Test
    void getSellerByUserID_ShouldReturnSeller_WhenRequestIsOK() throws Exception {
        Integer userId = 1;

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setUserId(userId);

        mockMvc.perform(get("/sellers/userId/"+userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isOk());

        sellerController.getSellerByUserId(userId);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenRequestIsOK() throws Exception {
        Integer id = 1;

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(id);

        mockMvc.perform(delete("/sellers/"+id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isNoContent());

        sellerController.deleteSeller(id);
    }

    @Test
    void updateSeller_ShouldUpdateSeller_WhenRequestIsOK() throws Exception {
        Integer id = 1;

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(id);

        mockMvc.perform(put("/sellers/"+id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sellerDTO)))
                .andExpect(status().isNoContent());

        sellerController.updateSeller(id, sellerDTO);
    }

}