package com.phatdo.ecommerce.arena.utils.cache;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface IWriteHash<T> {
    void writeHash(String key, T value) throws JsonProcessingException;
}
