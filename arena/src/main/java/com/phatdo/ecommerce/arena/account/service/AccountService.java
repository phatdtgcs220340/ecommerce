package com.phatdo.ecommerce.arena.account.service;

import com.phatdo.ecommerce.arena.account.domain.Account;
import com.phatdo.ecommerce.arena.account.domain.CustomUserDetail;
import com.phatdo.ecommerce.arena.account.repository.AccountRepository;
import com.phatdo.ecommerce.arena.account.request.RegisterDTO;
import com.phatdo.ecommerce.arena.utils.cache.AbstractHashMapping;
import com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaError.ACCOUNT_EXISTED;
import static com.phatdo.ecommerce.arena.utils.exceptionhandler.domain.ArenaError.ACCOUNT_NOT_FOUND;

@Slf4j
@Service
public class AccountService extends AbstractHashMapping<Account> implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    public static final String CACHE_KEY_PREFIX_ACCOUNT_BY_USERNAME = "ACCOUNT_BY_USERNAME_";

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder,
                          RedisTemplate<String, Account> redisTemplate) {
        super(redisTemplate);
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        account.setCustomer(null);
        account.getSellers().clear();
        return new CustomUserDetail(account);
    }

    public Account createNewAccount(RegisterDTO form) throws ArenaException {
        Optional<Account> optAccount = accountRepository.findByEmail(form.email());
        if (optAccount.isPresent())
            throw new ArenaException(ACCOUNT_EXISTED);
        Account newAccount = new Account(form.email());
        newAccount.setPassword(passwordEncoder.encode(form.password()));
        return accountRepository.save(newAccount);
    }

    public Account findById(UUID uuid) throws ArenaException {
        return this.accountRepository.findById(uuid).orElseThrow(() -> new ArenaException(ACCOUNT_NOT_FOUND));
    }

    public OidcUserInfo loadAccount(Account account) {
        String avatar = "";
        if (account.getRole().equals(Account.Role.CUSTOMER)) {
            avatar = account.getCustomer().getAvatar();
        }
        return new OidcUserInfo(OidcUserInfo.builder()
                .name(account.getCustomer().getFullName())
                .email(account.getEmail())
                .picture(avatar)
                .build().getClaims());
    }

    public Account loadAndCacheAccountByUsername(String key) {
        log.info("Try to load cache map {} for user detail", key);
        Account existedAccount = this.LoadHash().loadHash(key);
        if (Objects.isNull(existedAccount)) {
            log.info("Generating cache map {} for user detail", key);
            CustomUserDetail userDetail = (CustomUserDetail) this.loadUserByUsername(key.replace(CACHE_KEY_PREFIX_ACCOUNT_BY_USERNAME, ""));
            Account account = userDetail.account();
            this.WriteHash().writeHash(key, account);
            return account;
        }
        else
            return existedAccount;
    }
}
