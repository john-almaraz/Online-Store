package com.john_henry.User.infrastructure.adapters.output.persistence.repository;

import com.john_henry.User.domain.model.Seller;
import com.john_henry.User.infrastructure.adapters.output.persistence.entity.SellerEntity;
import com.john_henry.User.infrastructure.adapters.output.persistence.mapper.SellerPersistenceMapper;
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
class SellerRepositoryImplTest {

    @Mock
    private JpaSellerRepository jpaSellerRepository;

    @Mock
    private SellerPersistenceMapper sellerPersistenceMapper;

    @InjectMocks
    private SellerRepositoryImpl sellerRepository;

    @Test
    public void createSeller_ShouldReturnSeller_WhenSellerIsCreated(){
        String storeName = "John Store";

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setNameStore(storeName);

        Seller sellerExpected = new Seller();
        sellerExpected.setNameStore(storeName);

        when(sellerPersistenceMapper.toEntity(sellerExpected)).thenReturn(sellerEntity);
        when(jpaSellerRepository.save(sellerEntity)).thenReturn(sellerEntity);
        when(sellerPersistenceMapper.toDomain(sellerEntity)).thenReturn(sellerExpected);

        Seller result = sellerRepository.createSeller(sellerExpected);

        assertEquals(sellerExpected,result);
        verify(sellerPersistenceMapper).toEntity(sellerExpected);
        verify(jpaSellerRepository).save(sellerEntity);
        verify(sellerPersistenceMapper).toDomain(sellerEntity);

    }

    @Test
    public void getSellerById_ShouldReturnSeller_WhenSellerExist(){
        Integer id = 1;
        String storeName = "John Store";

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setNameStore(storeName);

        Seller sellerExpected = new Seller();
        sellerExpected.setNameStore(storeName);

        when(jpaSellerRepository.findById(id)).thenReturn(Optional.of(sellerEntity));
        when(sellerPersistenceMapper.toDomain(sellerEntity)).thenReturn(sellerExpected);

        Optional<Seller> result = sellerRepository.getSellerById(id);

        assertTrue(result.isPresent());
        assertEquals(sellerExpected,result.get());
        verify(jpaSellerRepository).findById(id);
        verify(sellerPersistenceMapper).toDomain(sellerEntity);

    }

    @Test
    public void getSellerByUserId_ShouldReturnSeller_WhenSellerExist(){
        Integer userId = 1;
        String storeName = "John Store";

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setNameStore(storeName);

        Seller sellerExpected = new Seller();
        sellerExpected.setNameStore(storeName);

        when(jpaSellerRepository.findByUserId(userId)).thenReturn(Optional.of(sellerEntity));
        when(sellerPersistenceMapper.toDomain(sellerEntity)).thenReturn(sellerExpected);

        Optional<Seller> result = sellerRepository.getSellerByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(sellerExpected,result.get());
        verify(jpaSellerRepository).findByUserId(userId);
        verify(sellerPersistenceMapper).toDomain(sellerEntity);

    }

    @Test
    public void getAllSellers_ShouldReturnListOfSellers_WhenSellersExist(){
        Integer id = 1;
        String nameStore = "John Store";

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setNameStore(nameStore);

        Seller seller = new Seller();
        seller.setNameStore(nameStore);

        List<SellerEntity> sellerEntities = new ArrayList<>();
        sellerEntities.add(sellerEntity);

        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        when(jpaSellerRepository.findAll()).thenReturn(sellerEntities);
        when(sellerPersistenceMapper.toListDomain(sellerEntities)).thenReturn(sellers);

        List<Seller> listResult = sellerRepository.getAllSellers();

        assertEquals(sellers,listResult);
        verify(jpaSellerRepository).findAll();
        verify(sellerPersistenceMapper).toListDomain(sellerEntities);

    }

    @Test
    public void updateSeller_ShouldUpdateSeller_WhenSellerExist(){
        Integer id = 1;
        String storeName = "John Store";

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setNameStore(storeName);

        Seller seller = new Seller();
        seller.setNameStore(storeName);

        when(jpaSellerRepository.findById(id)).thenReturn(Optional.of(sellerEntity));
        when(sellerPersistenceMapper.toEntity(seller)).thenReturn(sellerEntity);
        when(jpaSellerRepository.save(sellerEntity)).thenReturn(sellerEntity);

        sellerRepository.updateSeller(id,seller);

        verify(jpaSellerRepository).findById(id);
        verify(sellerPersistenceMapper).toEntity(seller);
        verify(jpaSellerRepository).save(sellerEntity);

    }

    @Test
    public void deleteSeller_ShouldDeleteSeller_WhenSellerExist(){
        Integer id = 1;

        doNothing().when(jpaSellerRepository).deleteById(id);

        sellerRepository.deleteSeller(id);

        verify(jpaSellerRepository).deleteById(id);

    }


}