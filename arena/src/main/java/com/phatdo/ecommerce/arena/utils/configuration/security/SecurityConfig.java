package com.phatdo.ecommerce.arena.utils.configuration.security;

import com.phatdo.ecommerce.arena.account.repository.AccountRepository;
import com.phatdo.ecommerce.arena.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.phatdo.ecommerce.arena.utils.commons.APIController.ACCOUNT_PATH;

@Configuration
public class SecurityConfig {
    private final AccountRepository accountRepository;
    @Autowired
    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public AccountService accountService() {
        return new AccountService(accountRepository, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(form -> form
                        .loginProcessingUrl("/api/account/login")
                        .defaultSuccessUrl("/authorization"))
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.POST, ACCOUNT_PATH, String.format("%s/**", ACCOUNT_PATH)).permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
