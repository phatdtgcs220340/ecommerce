package com.phatdo.ecommerce.arena.account.domain;


import java.util.UUID;

public record AccountKey(UUID uuid, String email) {
    public static AccountKey toDTO(Account account) {
        return new AccountKey(account.getUuid(), account.getEmail());
    }
}