package com.phatdo.ecommerce.arena.utils.configuration.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Set;

@Slf4j
@Configuration
public class OAuth2CustomToken {
    private final AccountService accountService;

    @Autowired
    public OAuth2CustomToken(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context ->
                context.getClaims().claims(claims -> {
                    String username = context.getPrincipal().getName();
                    Account account = null;
                    try {
                        account = accountService
                                .loadAndCacheAccountByUsername(String.format("%s%s",AccountService.CACHE_KEY_PREFIX_ACCOUNT_BY_USERNAME, username));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("{} - sub : {}", context.getTokenType().getValue(), username);
                    claims.put("account_uuid", account.getUuid());
                    Set<String> scopes = context.getAuthorizedScopes();
                    if (scopes.contains("SELLER"))
                        claims.put("role", "SELLER");
                    else
                        claims.put("role", "CUSTOMER");
                    if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                        if (scopes.contains("SELLER"))
                            account.setRole(Account.Role.SELLER);
                        else
                            account.setRole(Account.Role.CUSTOMER);
                        OidcUserInfo userInfo = accountService.loadAccount(account);
                        claims.putAll(userInfo.getClaims());
                    }
                });
    }
}