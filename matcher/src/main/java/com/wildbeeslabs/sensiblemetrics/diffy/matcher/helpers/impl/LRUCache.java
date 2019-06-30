package com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.impl;

import com.jayway.jsonpath.JsonPath;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.helpers.iface.Cache;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache implements Cache {

    private final ReentrantLock lock = new ReentrantLock();

    private final Map<String, JsonPath> map;
    private final Deque<String> queue;
    private final int limit;

    public LRUCache(final int limit) {
        this.limit = limit;
        map = new ConcurrentHashMap<>();
        queue = new LinkedList<>();
    }

    @Override
    public void put(final String key, final JsonPath value) {
        final JsonPath oldValue = this.map.put(key, value);
        if (Objects.nonNull(oldValue)) {
            this.removeThenAddKey(key);
        } else {
            this.addKey(key);
        }
        if (this.map.size() > this.limit) {
            this.map.remove(removeLast());
        }
    }

    public JsonPath get(final String key) {
        final JsonPath jsonPath = this.map.get(key);
        if (Objects.nonNull(jsonPath)) {
            this.removeThenAddKey(key);
        }
        return jsonPath;
    }

    private void addKey(final String key) {
        this.lock.lock();
        try {
            this.queue.addFirst(key);
        } finally {
            this.lock.unlock();
        }
    }

    private String removeLast() {
        this.lock.lock();
        try {
            final String removedKey = this.queue.removeLast();
            return removedKey;
        } finally {
            this.lock.unlock();
        }
    }

    private void removeThenAddKey(final String key) {
        this.lock.lock();
        try {
            this.queue.removeFirstOccurrence(key);
            this.queue.addFirst(key);
        } finally {
            this.lock.unlock();
        }

    }

    private void removeFirstOccurrence(final String key) {
        this.lock.lock();
        try {
            this.queue.removeFirstOccurrence(key);
        } finally {
            this.lock.unlock();
        }
    }

    public JsonPath getSilent(final String key) {
        return this.map.get(key);
    }

    public void remove(final String key) {
        this.removeFirstOccurrence(key);
        this.map.remove(key);
    }

    public int size() {
        return this.map.size();
    }

    public String toString() {
        return this.map.toString();
    }
}
