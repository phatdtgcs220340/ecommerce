package com.phatdo.ecommerce.arena.account.controller;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.request.RegisterDTO;
import com.phatdo.ecommerce.arena.account.service.AccountService;
import com.phatdo.ecommerce.arena.customer.response.CustomerDetailDTO;
import com.phatdo.ecommerce.arena.customer.service.CustomerService;
import com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.phatdo.ecommerce.arena.utils.commons.APIController.*;

@Slf4j
@RestController
@RequestMapping(ACCOUNT_PATH)
public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDetailDTO> createNewAccount(@RequestBody @Valid RegisterDTO form) throws ArenaException {
        log.info("Account with email {} is creating...", form.email());
        Account account = this.accountService.createNewAccount(form);
        return ResponseEntity.ok(CustomerDetailDTO.toDTO(this.customerService.createNewCustomer(account, form)));
    }

}
