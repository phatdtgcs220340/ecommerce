package com.phatdo.ecommerce.arena.account.domain;


import java.util.UUID;

public record AccountKey(UUID uuid, String email, String fullName) {
}