package com.phatdo.ecommerce.arena.seller.request;

public record NewSellerDTO(
        String name,
        String shopLogo,
        String telephone,
        String country,
        String city,
        String province,
        String district
) {
}
