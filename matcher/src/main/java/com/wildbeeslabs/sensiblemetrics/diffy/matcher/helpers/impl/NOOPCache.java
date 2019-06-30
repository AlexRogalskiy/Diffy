package com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.impl;

import com.jayway.jsonpath.JsonPath;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.iface.Cache;

public class NOOPCache implements Cache {

    @Override
    public JsonPath get(final String key) {
        return null;
    }

    @Override
    public void put(final String key, JsonPath value) {
    }
}
