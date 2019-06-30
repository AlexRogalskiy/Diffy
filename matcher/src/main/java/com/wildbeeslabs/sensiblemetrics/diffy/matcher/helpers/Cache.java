package com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers;

import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;

public interface Cache {

    /**
     * Get the Cached JsonPath
     *
     * @param key cache key to lookup the JsonPath
     * @return JsonPath
     */
    JsonPath get(final String key);

    /**
     * Add JsonPath to the cache
     *
     * @param key   cache key to store the JsonPath
     * @param value JsonPath to be cached
     * @throws InvalidJsonException
     */
    void put(final String key, final JsonPath value);
}
