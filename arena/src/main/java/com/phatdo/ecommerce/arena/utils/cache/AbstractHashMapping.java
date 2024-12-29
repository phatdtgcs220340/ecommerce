package com.phatdo.ecommerce.arena.utils.cache;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractHashMapping<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public AbstractHashMapping(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public IWriteHash<T> WriteHash() {
        return (key, object) -> {
            redisTemplate.opsForValue().set(key, object);
        };
    }

    public ILoadHash<T> LoadHash() {
        return key -> redisTemplate.opsForValue().get(key);
    };
}
