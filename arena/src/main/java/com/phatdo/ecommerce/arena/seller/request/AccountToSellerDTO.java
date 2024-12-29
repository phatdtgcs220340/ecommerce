package com.phatdo.ecommerce.arena.seller.request;

import com.phatdo.ecommerce.arena.seller.domain.AccountSeller;

import java.util.UUID;

public record AccountToSellerDTO(UUID accountId, UUID sellerId, AccountSeller.Permission permission) {
}
