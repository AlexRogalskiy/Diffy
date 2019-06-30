package com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.impl;

import com.jayway.jsonpath.JsonPathException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.iface.Cache;

import java.util.Objects;

public class CacheProvider {
    private static Cache cache;
    private static boolean cachingEnabled;

    public static void setCache(final Cache cache) {
        ValidationUtils.notNull(cache, "Cache may not be null");
        synchronized (CacheProvider.class) {
            if (Objects.nonNull(CacheProvider.cache)) {
                throw new JsonPathException("Cache provider must be configured before cache is accessed.");
            } else {
                CacheProvider.cache = cache;
            }
            cachingEnabled = !(CacheProvider.cache instanceof NOOPCache);
        }
    }

    public static Cache getCache() {
        if (Objects.isNull(CacheProvider.cache)) {
            synchronized (CacheProvider.class) {
                if (Objects.isNull(CacheProvider.cache)) {
                    CacheProvider.cache = getDefaultCache();
                }
            }
        }
        return CacheProvider.cache;
    }

    private static Cache getDefaultCache() {
        return new LRUCache(400);
        //return new NOOPCache();
    }
}
