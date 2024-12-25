package com.phatdo.ecommerce.arena.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder; // Omitted constructor

    @Autowired
    public CustomAuthenticationProvider(AccountService accountService,
                                        PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails u = accountService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, u.getPassword())) {
            log.info("Authenticated user detail {}", username);
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        } else {
            log.error("Failed to authenticate user detail {}", username);
            throw new BadCredentialsException("Something went wrong!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}