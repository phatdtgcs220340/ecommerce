package com.phatdo.ecommerce.arena.account.request;

import com.phatdo.ecommerce.arena.account.domain.Account;

public record LoginDTO(String username, String password, Account.Role loginAs) {
}
