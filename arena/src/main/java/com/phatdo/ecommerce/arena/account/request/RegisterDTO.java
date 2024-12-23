package com.phatdo.ecommerce.arena.account.request;

public record RegisterDTO(String email,
                          String password,
                          String fullName) {
}
