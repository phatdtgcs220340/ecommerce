package com.phatdo.ecommerce.arena.utils.cache;

import com.phatdo.ecommerce.arena.utils.commons.APIController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(APIController.CACHE_PATH)
public class CacheController {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CacheController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/cache-evict/cache-by-user")
    public void clearCache(Authentication authentication) {
        Set<String> keys = redisTemplate.keys(authentication.getName());

        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }
}
