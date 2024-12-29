package com.phatdo.ecommerce.arena.customer.response;

import com.phatdo.ecommerce.arena.account.domain.AccountKey;
import com.phatdo.ecommerce.arena.customer.domain.Customer;

import java.util.UUID;

public record CustomerDetailDTO(
        AccountKey key,
        UUID customerId,
        String fullName,
        String avatar,
        String telephone,
        String country
) {
    public static CustomerDetailDTO toDTO(Customer customer) {
        AccountKey key = AccountKey.toDTO(customer.getAccount());
        return new CustomerDetailDTO(key, customer.getUuid(), customer.getFullName(), customer.getAvatar(), customer.getTelephone(), customer.getCountry());
    }
}
