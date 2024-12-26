package com.phatdo.ecommerce.arena.account.controller;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.request.LoginDTO;
import com.phatdo.ecommerce.arena.account.request.RegisterDTO;
import com.phatdo.ecommerce.arena.account.service.AccountService;
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
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createNewAccount(@RequestBody @Valid RegisterDTO form) throws ArenaException {
        log.info("Account with email {} is creating...", form.email());
        return ResponseEntity.ok(this.accountService.createNewAccount(form));
    }

}
