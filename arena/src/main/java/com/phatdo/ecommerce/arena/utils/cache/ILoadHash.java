package com.phatdo.ecommerce.arena.utils.cache;

@FunctionalInterface
public interface ILoadHash<T> {
    T loadHash(String key);
}
