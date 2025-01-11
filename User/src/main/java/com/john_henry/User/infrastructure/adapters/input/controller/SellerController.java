package com.john_henry.User.infrastructure.adapters.input.controller;

import com.john_henry.User.aplication.dto.SellerDTO;
import com.john_henry.User.aplication.ports.input.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<SellerDTO> createSeller (@RequestBody SellerDTO sellerDTO){
        return new ResponseEntity<SellerDTO>(sellerService.createSeller(sellerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<SellerDTO> getSellerByUserId (@PathVariable Integer userId){
        return ResponseEntity.ok(sellerService.getSellerByUserId(userId));
    }
    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Integer sellerId){
        return ResponseEntity.ok(sellerService.getSellerById(sellerId));
    }

    @GetMapping("/allSellers")
    public ResponseEntity<List<SellerDTO>> getAllSellers (){
        return ResponseEntity.ok(sellerService.getAllSellers());
    }
    @DeleteMapping("/{sellerId}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Integer sellerId){
        sellerService.deleteSeller(sellerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{sellerId}")
    public ResponseEntity<Void> updateSeller(@PathVariable Integer sellerId, @RequestBody SellerDTO sellerDTO){
        sellerService.updateSeller(sellerId,sellerDTO);
        return  ResponseEntity.noContent().build();
    }

}
