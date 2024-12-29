package com.phatdo.ecommerce.arena.utils.cache;

@FunctionalInterface
public interface IWriteHash<T> {
    void writeHash(String key, T value);
}
