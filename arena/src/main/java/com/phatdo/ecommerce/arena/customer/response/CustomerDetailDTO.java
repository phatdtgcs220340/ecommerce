package com.phatdo.ecommerce.arena.customer.response;

import com.phatdo.ecommerce.arena.account.domain.AccountKey;
import com.phatdo.ecommerce.arena.customer.domain.Customer;

import java.util.UUID;

public record CustomerDetailDTO(
        AccountKey key,
        UUID customerId,
        String avatar,
        String telephone,
        String country
) {
    public static CustomerDetailDTO toDTO(Customer customer) {
        AccountKey key = new AccountKey(customer.getAccount().getUuid(), customer.getAccount().getEmail(), customer.getAccount().getFullName());
        return new CustomerDetailDTO(key, customer.getUuid(), customer.getAvatar(), customer.getTelephone(), customer.getCountry());
    }
}
