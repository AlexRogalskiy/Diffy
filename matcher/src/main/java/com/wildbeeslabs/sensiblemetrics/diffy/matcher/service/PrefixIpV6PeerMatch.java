package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

import java.net.InetAddress;

public class PrefixIpV6PeerMatch implements Matcher<InetAddress> {

    private final byte[] mask;
    private final byte[] prefix;

    protected PrefixIpV6PeerMatch(final byte[] mask, final byte[] prefix) {
        this.mask = mask;
        this.prefix = prefix;
        assert mask.length == prefix.length;
    }

    @Override
    public boolean matches(final InetAddress address) {
        byte[] bytes = address.getAddress();
        if (bytes == null) {
            return false;
        }
        if (bytes.length != mask.length) {
            return false;
        }
        for (int i = 0; i < mask.length; ++i) {
            if ((bytes[i] & mask[i]) != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
