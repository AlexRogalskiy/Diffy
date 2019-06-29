package com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers;

import com.jayway.jsonpath.JsonPath;

public class NOOPCache implements Cache {

    @Override
    public JsonPath get(final String key) {
        return null;
    }

    @Override
    public void put(final String key, JsonPath value) {
    }
}
