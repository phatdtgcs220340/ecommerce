package com.phatdo.ecommerce.arena.utils.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractHashMapping<T> {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractHashMapping(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public IWriteHash<T> WriteHash() {
        return (key, object) -> {
            String serializedObject = mapper.writeValueAsString(object);
            redisTemplate.opsForValue().set(key, serializedObject);
        };
    }

    public ILoadHash<T> LoadHash(Class<T> clazz) {
        return key -> {
            String receivedValue = redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(receivedValue))
                return null;
            return mapper.readValue(receivedValue, clazz);
        };
    };
}
