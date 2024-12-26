package com.phatdo.ecommerce.arena.account.service;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.domain.CustomUserDetail;
import com.phatdo.ecommerce.arena.account.repository.AccountRepository;
import com.phatdo.ecommerce.arena.account.request.LoginDTO;
import com.phatdo.ecommerce.arena.account.request.RegisterDTO;
import com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaError.ACCOUNT_EXISTED;

@Slf4j
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new CustomUserDetail(account);
    }

    public Account createNewAccount(RegisterDTO form) throws ArenaException {
        Optional<Account> optAccount = accountRepository.findByEmail(form.email());
        if (optAccount.isPresent())
            throw new ArenaException(ACCOUNT_EXISTED);
        Account newAccount = new Account(form.email());
        newAccount.setPassword(passwordEncoder.encode(form.password()));
        newAccount.setFullName(form.fullName());
        return accountRepository.save(newAccount);
    }

    public OidcUserInfo loadAccount(Account account) {
        return new OidcUserInfo(OidcUserInfo.builder()
                .name(account.getFullName())
                .email(account.getEmail())
                .build().getClaims());
    }
}
