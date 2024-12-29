package com.phatdo.ecommerce.arena.utils.exceptionhandler.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ArenaError {
    ACCOUNT_EXISTED("Account existed", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND("Account not found", HttpStatus.NOT_FOUND),
    SELLER_NOT_FOUND("Seller not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
    ArenaError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
