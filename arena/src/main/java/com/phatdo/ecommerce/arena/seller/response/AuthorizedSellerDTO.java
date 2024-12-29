package com.phatdo.ecommerce.arena.seller.response;

import com.phatdo.ecommerce.arena.seller.domain.AccountSeller;
import com.phatdo.ecommerce.arena.seller.domain.Seller;

import java.util.UUID;

public record AuthorizedSellerDTO(
        UUID uuid,
        String name,
        String shopLogo,
        String permission
) {
    public static AuthorizedSellerDTO toDTO(Seller seller, AccountSeller.Permission permission) {
        return new AuthorizedSellerDTO(seller.getUuid(), seller.getName(), seller.getShopLogo(), permission.name());
    }
}
