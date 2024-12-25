package com.phatdo.ecommerce.arena.utils.exceptionhandler.response;

import java.util.Map;

public record FailedResponse(
        String message,
        Map<String, String> cause
) {
}
