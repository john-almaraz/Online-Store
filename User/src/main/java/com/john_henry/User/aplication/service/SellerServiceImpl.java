package com.john_henry.User.aplication.service;

import com.john_henry.User.aplication.dto.SellerDTO;
import com.john_henry.User.aplication.mapper.SellerMapper;
import com.john_henry.User.aplication.ports.input.SellerService;
import com.john_henry.User.aplication.ports.output.SellerRepository;
import com.john_henry.User.aplication.ports.output.UserRepository;
import com.john_henry.User.domain.exception.SellerNotFoundException;
import com.john_henry.User.domain.exception.UserNotFoundException;
import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final UserRepository userRepository;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper, UserRepository userRepository) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.userRepository = userRepository;
    }


    @Override
    public SellerDTO createSeller(SellerDTO sellerDTO) {
        Optional<User> user = userRepository.getUserById(sellerDTO.getUserId());

        if(user.isEmpty()) {
            throw new UserNotFoundException(
                    "User with id: " + sellerDTO.getUserId() + " not exist, you must first create a user");
        }

        return sellerMapper.toDTO(sellerRepository.createSeller(sellerMapper.toEntity(sellerDTO)));
    }

    @Override
    public SellerDTO getSellerById(Integer sellerId) {
        Seller seller = sellerRepository.getSellerById(sellerId).orElseThrow(
                ()-> new SellerNotFoundException("Seller with id: " + sellerId + " not found"));

        return sellerMapper.toDTO(seller);
    }

    @Override
    public SellerDTO getSellerByUserId(Integer userId) {
        Seller seller = sellerRepository.getSellerByUserId(userId).orElseThrow(
                ()-> new SellerNotFoundException("Seller with userID: " + userId + " not found"));

        return sellerMapper.toDTO(seller);
    }

    @Override
    public List<SellerDTO> getAllSellers() {
        return sellerMapper.toListDTO(sellerRepository.getAllSellers());
    }

    @Override
    public void updateSeller(Integer sellerId, SellerDTO sellerDTO) {
        Seller seller = sellerRepository.getSellerById(sellerId).orElseThrow(
                ()-> new SellerNotFoundException("Seller with id: " + sellerId + " not found")
        );
        sellerMapper.updateFromDto(sellerDTO,seller);

        sellerRepository.updateSeller(sellerId,seller);
    }

    @Override
    public void deleteSeller(Integer sellerId) {
        if (sellerRepository.getSellerById(sellerId).isEmpty())
            throw new SellerNotFoundException("Seller with id: " + sellerId + " not found");

        sellerRepository.deleteSeller(sellerId);
    }
}
