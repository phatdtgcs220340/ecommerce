package com.phatdo.ecommerce.arena.account.domain;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("customer"),
    SELLER("seller"),
    ADMINISTRATOR("admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
