package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

import java.net.InetAddress;

public class PrefixIpV4PeerMatch implements Matcher<InetAddress> {
    private final int mask;
    private final int prefix;

    protected PrefixIpV4PeerMatch(final int mask, final int prefix) {
        this.mask = mask;
        this.prefix = prefix;
    }

    @Override
    public boolean matches(final InetAddress address) {
        byte[] bytes = address.getAddress();
        if (bytes == null) {
            return false;
        }
        int addressInt = ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        return (addressInt & mask) == prefix;
    }
}
