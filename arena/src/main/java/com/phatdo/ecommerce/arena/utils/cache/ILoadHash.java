package com.phatdo.ecommerce.arena.utils.cache;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface ILoadHash<T> {
    T loadHash(String key) throws JsonProcessingException;
}
