package com.phatdo.ecommerce.arena.seller.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.seller.domain.Seller;
import com.phatdo.ecommerce.arena.seller.request.NewSellerDTO;
import com.phatdo.ecommerce.arena.seller.response.AuthorizedSellerDTO;
import com.phatdo.ecommerce.arena.seller.response.SellerDTO;
import com.phatdo.ecommerce.arena.seller.service.SellerService;
import com.phatdo.ecommerce.arena.utils.commons.APIController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(APIController.SELLER_PATH)
public class SellerController {

    private final SellerService service;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.service = sellerService;
    }

    @PostMapping
    public ResponseEntity<SellerDTO> createNewSeller(@RequestBody NewSellerDTO request,
                                                     Authentication authentication) throws JsonProcessingException {
        Seller seller = this.service.createNewSeller(request, authentication.getName());
        return ResponseEntity.ok(SellerDTO.toDTO(seller));
    }

    @GetMapping("/authorized")
    public ResponseEntity<Page<AuthorizedSellerDTO>> findAllAuthorizedSeller(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             Authentication authentication) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.service.findAllAuthorizedSeller(authentication.getName(), pageable));
    }
}
