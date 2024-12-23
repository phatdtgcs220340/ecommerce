package com.phatdo.ecommerce.arena.seller.domain;

import com.phatdo.ecommerce.arena.account.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Seller {
    @Id
    @Column(unique = true)
    private final UUID uuid = new UUID(1, UUID.randomUUID().getMostSignificantBits());

    @ManyToOne
    @JoinColumn(name = "account_uuid")
    private final Account account;
}