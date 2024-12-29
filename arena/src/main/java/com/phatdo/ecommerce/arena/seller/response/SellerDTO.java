package com.phatdo.ecommerce.arena.seller.response;

import com.phatdo.ecommerce.arena.seller.domain.Seller;

public record SellerDTO(
        String name,
        String telephone,
        String shopLogo,
        String address
) {
    public static SellerDTO toDTO(Seller seller) {
        String address = String.format("%s District - %s City - %s Province - %s",
                seller.getDistrict(),
                seller.getCity(),
                seller.getProvince(),
                seller.getCountry());
        return new SellerDTO(seller.getName(), seller.getTelephone(), seller.getShopLogo(), address);
    }
}
