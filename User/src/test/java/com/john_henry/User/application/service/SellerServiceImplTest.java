package com.john_henry.User.application.service;

import com.john_henry.User.application.dto.SellerDTO;
import com.john_henry.User.application.mapper.SellerMapper;
import com.john_henry.User.application.ports.output.SellerRepository;
import com.john_henry.User.application.ports.output.UserRepository;
import com.john_henry.User.domain.exception.SellerNotFoundException;
import com.john_henry.User.domain.exception.UserNotFoundException;
import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Test
    void createSeller_ShouldReturnSellerDTO_WhenSellerIsCreated() {
        Integer userId = 1;
        String nameStore = "John Store";

        User user = new User();
        user.setId(userId);

        Seller seller = new Seller();
        seller.setUserId(userId);
        seller.setNameStore(nameStore);

        SellerDTO sellerDTOExpected = new SellerDTO();
        sellerDTOExpected.setUserId(userId);
        sellerDTOExpected.setNameStore(nameStore);


        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));
        when(sellerMapper.toEntity(sellerDTOExpected)).thenReturn(seller);
        when(sellerRepository.createSeller(seller)).thenReturn(seller);
        when(sellerMapper.toDTO(seller)).thenReturn(sellerDTOExpected);

        SellerDTO result = sellerService.createSeller(sellerDTOExpected);

        assertEquals(sellerDTOExpected,result);
        verify(userRepository).getUserById(userId);
        verify(sellerMapper).toEntity(sellerDTOExpected);
        verify(sellerRepository).createSeller(seller);
        verify(sellerMapper).toDTO(seller);

    }

    @Test
    void createSeller_ShouldThrowUserNotFoundException_WhenUserNotFound(){
        Integer userId = 1;
        String messageExpected = "User with id: 1 not exist, you must first create a user";

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setUserId(userId);

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> sellerService.createSeller(sellerDTO)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(userRepository).getUserById(userId);

    }

    @Test
    void getSellerById_ShouldReturnSeller_WhenSellerExist() {
        Integer sellerId = 1;

        Seller seller = new Seller();
        seller.setId(sellerId);

        SellerDTO sellerDTOExpected = new SellerDTO();
        sellerDTOExpected.setId(sellerId);

        when(sellerRepository.getSellerById(sellerId)).thenReturn(Optional.of(seller));
        when(sellerMapper.toDTO(seller)).thenReturn(sellerDTOExpected);

        SellerDTO result = sellerService.getSellerById(sellerId);

        assertEquals(sellerDTOExpected,result);
        verify(sellerRepository).getSellerById(sellerId);
        verify(sellerMapper).toDTO(seller);

    }

    @Test
    void getSellerById_ShouldThrowSellerNotFoundException_WhenSellerNotFound(){
        Integer sellerId = 1;
        String messageExpected = "Seller with id: 1 not found";

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(sellerId);

        when(sellerRepository.getSellerById(sellerId)).thenReturn(Optional.empty());

        SellerNotFoundException exception = assertThrows(SellerNotFoundException.class,
                () -> sellerService.getSellerById(sellerId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(sellerRepository).getSellerById(sellerId);

    }

    @Test
    void getSellerByUserId_ShouldReturnSeller_WhenSellerExist() {
        Integer userId = 1;

        Seller seller = new Seller();
        seller.setUserId(userId);

        SellerDTO sellerDTOExpected = new SellerDTO();
        sellerDTOExpected.setUserId(userId);

        when(sellerRepository.getSellerByUserId(userId)).thenReturn(Optional.of(seller));
        when(sellerMapper.toDTO(seller)).thenReturn(sellerDTOExpected);

        SellerDTO result = sellerService.getSellerByUserId(userId);

        assertEquals(sellerDTOExpected,result);
        verify(sellerRepository).getSellerByUserId(userId);
        verify(sellerMapper).toDTO(seller);

    }

    @Test
    void getSellerByUserId_ShouldThrowSellerNotFoundException_WhenSellerNotFound(){
        Integer sellerId = 1;
        String messageExpected = "Seller with userID: 1 not found";

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(sellerId);

        when(sellerRepository.getSellerByUserId(sellerId)).thenReturn(Optional.empty());

        SellerNotFoundException exception = assertThrows(SellerNotFoundException.class,
                () -> sellerService.getSellerByUserId(sellerId)
        );

        assertEquals(messageExpected,exception.getMessage());
        verify(sellerRepository).getSellerByUserId(sellerId);

    }

    @Test
    void getAllSellers_ShouldReturnSellers_WhenSellersExist(){
        Integer id = 2;
        Integer userId = 1;
        String nameStore = "John Store";
        String logoStore = "src/pictures/store.png";
        String descriptionStore = "Cat Store";

        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(id, userId,nameStore,logoStore,descriptionStore));

        List<SellerDTO> sellersExpected = new ArrayList<>();
        sellersExpected.add(new SellerDTO(id, userId,nameStore,logoStore,descriptionStore));

        when(sellerRepository.getAllSellers()).thenReturn(sellers);
        when(sellerMapper.toListDTO(sellers)).thenReturn(sellersExpected);

        List<SellerDTO> result = sellerService.getAllSellers();

        assertEquals(sellersExpected,result);
        verify(sellerRepository).getAllSellers();
        verify(sellerMapper).toListDTO(sellers);

    }

    @Test
    void updateSeller_ShouldThrowException_WhenSellerIsNotFound() {
        Integer sellerId = 1;
        String messageExpected = "Seller with id: 1 not found";

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(sellerId);

        when(sellerRepository.getSellerById(sellerId)).thenReturn(Optional.empty());

        SellerNotFoundException exception = assertThrows(SellerNotFoundException.class,
                () -> sellerService.updateSeller(sellerId,sellerDTO)
        );

        assertEquals(messageExpected, exception.getMessage());

        verify(sellerRepository).getSellerById(sellerId);
    }

    @Test
    void updateSeller_ShouldUpdateSellerWithoutErrors_WhenSellerExist() {
        Integer sellerId = 1;
        String nameStore = "John Store";

        Seller seller = new Seller();
        seller.setId(sellerId);

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(sellerId);
        sellerDTO.setNameStore(nameStore);

        when(sellerRepository.getSellerById(sellerId)).thenReturn(Optional.of(seller));
        doNothing().when(sellerMapper).updateFromDto(sellerDTO,seller);
        doNothing().when(sellerRepository).updateSeller(sellerId,seller);

        sellerService.updateSeller(sellerId,sellerDTO);

        verify(sellerRepository).getSellerById(sellerId);
        verify(sellerMapper).updateFromDto(sellerDTO,seller);
        verify(sellerRepository).updateSeller(sellerId,seller);

    }

    @Test
    void deleteSeller_ShouldThrowException_WhenSellerIsNotFound() {
        Integer id = 1;
        String messageExpected = "Seller with id: 1 not found";

        when(sellerRepository.getSellerById(id)).thenReturn(Optional.empty());

        SellerNotFoundException exception = assertThrows(SellerNotFoundException.class,
                () -> sellerService.deleteSeller(id)
        );

        assertEquals(messageExpected, exception.getMessage());

        verify(sellerRepository).getSellerById(id);

    }

    @Test
    void deleteSeller_ShouldDeleteSellerWithoutErrors_WhenSellerExist(){
        Integer id = 1;

        Seller seller = new Seller();
        seller.setId(id);

        when(sellerRepository.getSellerById(id)).thenReturn(Optional.of(seller));
        doNothing().when(sellerRepository).deleteSeller(id);

        sellerService.deleteSeller(id);

        verify(sellerRepository).getSellerById(id);
        verify(sellerRepository).deleteSeller(id);

    }
}