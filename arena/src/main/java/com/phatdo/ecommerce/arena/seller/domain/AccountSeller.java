package com.phatdo.ecommerce.arena.seller.domain;

import com.phatdo.ecommerce.arena.account.domain.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class AccountSeller {
    @Id
    @Column(unique = true)
    private final UUID uuid = new UUID(1, UUID.randomUUID().getMostSignificantBits());

    @ManyToOne
    @JoinColumn(name = "account_uuid", nullable = false)
    private final Account account;

    @ManyToOne
    @JoinColumn(name = "seller_uuid", nullable = false)
    private final Seller seller;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    private long dateAudited = Instant.now().getEpochSecond();

    public enum Permission {
        OWNER,
        ADMINISTRATOR,
        STAFF
    }
}
