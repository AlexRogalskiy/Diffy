/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.MultiKey;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * A <code>DefaultMultiKey</code> allows multiple map keys to be merged together.
 * <p>
 * The purpose of this class is to avoid the need to write code to handle
 * maps of maps. An example might be the need to lookup a filename by
 * key and locale. The typical solution might be nested maps. This class
 * can be used instead by creating an instance passing in the key and locale.
 * <p>
 * Example usage:
 * <pre>
 * // populate map with data mapping key+locale to localizedText
 * Map map = new HashMap();
 * DefaultMultiKey multiKey = new DefaultMultiKey(key, locale);
 * map.put(multiKey, localizedText);
 *
 * // later retireve the localized text
 * DefaultMultiKey multiKey = new DefaultMultiKey(key, locale);
 * String localizedText = (String) map.get(multiKey);
 * </pre>
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultMultiKey<T> implements MultiKey<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4681950012252782050L;

    /**
     * Default array of {@code T} items
     */
    private final T[] keys;

    /**
     * Default hashcode value
     */
    private transient int hashCode;

    /**
     * Constructor taking two keys.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     *
     * @param key1 the first key
     * @param key2 the second key
     */
    public DefaultMultiKey(final T key1, final T key2) {
        this((T[]) new Object[]{key1, key2}, false);
    }

    /**
     * Constructor taking three keys.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     */
    public DefaultMultiKey(final T key1, final T key2, final T key3) {
        this((T[]) new Object[]{key1, key2, key3}, false);
    }

    /**
     * Constructor taking four keys.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     */
    public DefaultMultiKey(final T key1, final T key2, final T key3, final T key4) {
        this((T[]) new Object[]{key1, key2, key3, key4}, false);
    }

    /**
     * Constructor taking five keys.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param key3 the third key
     * @param key4 the fourth key
     * @param key5 the fifth key
     */
    public DefaultMultiKey(final T key1, final T key2, final T key3, final T key4, final T key5) {
        this((T[]) new Object[]{key1, key2, key3, key4, key5}, false);
    }

    /**
     * Constructor taking an array of keys which is cloned.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     * <p>
     * This is equivalent to <code>new DefaultMultiKey(keys, true)</code>.
     *
     * @param keys the array of keys, not null
     * @throws IllegalArgumentException if the key array is null
     */
    public DefaultMultiKey(final T... keys) {
        this(keys, true);
    }

    /**
     * Constructor taking an array of keys, optionally choosing whether to clone.
     * <p>
     * <b>If the array is not cloned, then it must not be modified.</b>
     * <p>
     * This method is public for performance reasons only, to avoid a clone.
     * The hashcode is calculated once here in this method.
     * Therefore, changing the array passed in would not change the hashcode but
     * would change the equals method, which is a bug.
     * <p>
     * This is the only fully safe usage of this constructor, as the object array
     * is never made available in a variable:
     * <pre>
     * new DefaultMultiKey(new Object[] {...}, false);
     * </pre>
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed after adding to the DefaultMultiKey.
     *
     * @param keys      the array of keys, not null
     * @param makeClone true to clone the array, false to assign it
     * @throws IllegalArgumentException if the key array is null
     * @since Commons Collections 3.1
     */
    public DefaultMultiKey(final T[] keys, boolean makeClone) {
        ValidationUtils.notNull(keys, "Keys should not be null");
        if (makeClone) {
            this.keys = (T[]) keys.clone();
        } else {
            this.keys = keys;
        }
        this.calculateHashCode(keys);
    }

    //-----------------------------------------------------------------------

    /**
     * Gets a clone of the array of keys.
     * <p>
     * The keys should be immutable
     * If they are not then they must not be changed.
     *
     * @return the individual keys
     */
    @Override
    public T[] getKeys() {
        return (T[]) keys.clone();
    }

    /**
     * Gets the key at the specified index.
     * <p>
     * The key should be immutable.
     * If it is not then it must not be changed.
     *
     * @param index the index to retrieve
     * @return the key at the index
     * @throws IndexOutOfBoundsException if the index is invalid
     * @since Commons Collections 3.1
     */
    @Override
    public T getKey(int index) {
        ValidationUtils.isTrue(index >= 0 && index < this.keys.length, "Index is out of array bounds");
        return this.keys[index];
    }

    /**
     * Gets the size of the list of keys.
     *
     * @return the size of the list of keys
     * @since Commons Collections 3.1
     */
    @Override
    public int size() {
        return ArrayUtils.getLength(this.keys);
    }

    /**
     * Calculate the hash code of the instance using the provided keys.
     *
     * @param keys
     */
    private void calculateHashCode(final T[] keys) {
        int total = 0;
        for (int i = 0; i < keys.length; i++) {
            if (Objects.nonNull(keys[i])) {
                total ^= keys[i].hashCode();
            }
        }
        this.hashCode = total;
    }

    /**
     * Recalculate the hash code after deserialization.
     * The hash code of some keys might have change (hash codes based
     * on the system hash code are only stable for the same process).
     *
     * @return the instance with recalculated hash code
     */
    private Object readResolve() {
        this.calculateHashCode(keys);
        return this;
    }
}
