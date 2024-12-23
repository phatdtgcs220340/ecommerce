package com.phatdo.ecommerce.arena.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.phatdo.ecommerce.arena.utils.commons.APIController.*;

@Slf4j
@RestController
@RequestMapping(ACCOUNT_PATH)
public class AccountController {

    @GetMapping("/hello")
    public void ping() {
        log.info("hello");
    }
}
