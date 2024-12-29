package com.phatdo.ecommerce.arena.utils.configuration.oauth2;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.domain.CustomUserDetail;
import com.phatdo.ecommerce.arena.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.List;

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
                    if (((List<String>) claims.get("scope")).contains("SELLER"))
                        claims.put("role", "SELLER");
                    else
                        claims.put("role", "CUSTOMER");

                    String username = context.getPrincipal().getName();
                    Account account = ((CustomUserDetail) accountService
                            .loadUserByUsername(username))
                            .account();
                    claims.put("uuid", account.getUuid());

                    if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                        if (((List<String>) claims.get("scope")).contains("SELLER"))
                            account.setRole(Account.Role.SELLER);
                        else
                            account.setRole(Account.Role.CUSTOMER);
                        OidcUserInfo userInfo = accountService.loadAccount(account);
                        claims.putAll(userInfo.getClaims());
                    }
                });
    }
}