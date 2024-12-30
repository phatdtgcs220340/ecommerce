package com.phatdo.ecommerce.arena.seller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.domain.CustomUserDetail;
import com.phatdo.ecommerce.arena.account.service.AccountService;
import com.phatdo.ecommerce.arena.seller.domain.AccountSeller;
import com.phatdo.ecommerce.arena.seller.repository.AccountSellerRepository;
import com.phatdo.ecommerce.arena.seller.domain.Seller;
import com.phatdo.ecommerce.arena.seller.repository.SellerRepository;
import com.phatdo.ecommerce.arena.seller.request.AccountToSellerDTO;
import com.phatdo.ecommerce.arena.seller.request.NewSellerDTO;
import com.phatdo.ecommerce.arena.seller.response.AuthorizedSellerDTO;
import com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaError.SELLER_NOT_FOUND;

@Slf4j
@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final AccountService accountService;
    private final AccountSellerRepository accountSellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository,
                         AccountSellerRepository accountSellerRepository,
                         AccountService accountService) {
        this.sellerRepository = sellerRepository;
        this.accountSellerRepository = accountSellerRepository;
        this.accountService = accountService;
    }

    public AccountSeller linkAccountToSeller(AccountToSellerDTO request) throws ArenaException {
        Account account = accountService.findById(request.accountId());
        Seller seller = this.findById(request.sellerId());

        log.info("Generate permission {} from account {} to seller {}", request.permission(), account.getEmail(), seller.getName());

        Optional<AccountSeller> optExistedAccountSeller = accountSellerRepository.findByAccountUuidAndSellerUuid(request.accountId(), seller.getUuid());
        if (optExistedAccountSeller.isPresent()) {
            AccountSeller existedAccountSeller = optExistedAccountSeller.get();
            existedAccountSeller.setPermission(request.permission());
            return accountSellerRepository.save(existedAccountSeller);
        }
        AccountSeller accountSeller = new AccountSeller(account, seller);
        accountSeller.setPermission(request.permission());

        return accountSellerRepository.save(accountSeller);
    }

    public Seller findById(UUID uuid) throws ArenaException {
        return this.sellerRepository.findById(uuid).orElseThrow(() -> new ArenaException(SELLER_NOT_FOUND));
    }

    public Seller createNewSeller(NewSellerDTO request, String username) throws JsonProcessingException {
        Account account = this.accountService.loadAndCacheAccountByUsername(AccountService.CACHE_KEY_PREFIX_ACCOUNT_BY_USERNAME + username);

        log.info("Creating new shop {}", request.name());
        Seller newSeller = new Seller();
        newSeller.setName(request.name());
        newSeller.setShopLogo(request.shopLogo());
        newSeller.setTelephone(request.telephone());
        newSeller.setCity(request.city());
        newSeller.setCountry(request.country());
        newSeller.setProvince(request.province());
        newSeller.setDistrict(request.district());
        Seller seller = this.sellerRepository.save(newSeller);

        log.info("Generating permission {} from account {} to seller {}", AccountSeller.Permission.OWNER, account.getEmail(), request.name());
        AccountSeller accountSeller = new AccountSeller(account, newSeller);
        accountSeller.setPermission(AccountSeller.Permission.OWNER);
        this.accountSellerRepository.save(accountSeller);

        return seller;
    }

    public Page<AuthorizedSellerDTO> findAllAuthorizedSeller(String username, Pageable pageable) throws JsonProcessingException {
        Account account = this.accountService.loadAndCacheAccountByUsername(AccountService.CACHE_KEY_PREFIX_ACCOUNT_BY_USERNAME + username);

        log.info("Fetch authorized seller list...");
        return this.accountSellerRepository.findByAccount_Uuid(account.getUuid(), pageable)
                .map(entity -> AuthorizedSellerDTO.toDTO(entity.getSeller(), entity.getPermission()));
    }
}
